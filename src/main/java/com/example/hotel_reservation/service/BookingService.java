package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.event.Event;
import com.example.hotel_reservation.exception.CantReserveRoomException;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.kafka.KafkaMessage;
import com.example.hotel_reservation.kafka.RoomBookingMessage;
import com.example.hotel_reservation.repository.BookingRepository;
import com.example.hotel_reservation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    private final EventService eventService;

    @Value("${app.kafka.topics.room-booking}")
    private String bookingTopic;

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> getAllByUser(User user) {
        return bookingRepository.findAllByUser(user);
    }

    public Booking getById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Booking with ID {0} not found!", id))
        );
    }

    public Booking create(Booking booking) {
        if (bookingRepository.dateIsReserved(booking.getRoom().getId(), booking.getCheckIn(), booking.getCheckOut()))
            throw new CantReserveRoomException(
                    MessageFormat.format(
                            "The dates from {0} to {1} not available!", booking.getCheckIn(), booking.getCheckOut()
                    )
            );

        booking = bookingRepository.save(booking);
        RoomBookingMessage bookingMessage = new RoomBookingMessage();
        bookingMessage.setUserId(booking.getUser().getId());
        bookingMessage.setCheckIn(booking.getCheckIn());
        bookingMessage.setCheckOut(booking.getCheckOut());

        kafkaTemplate.send(bookingTopic, bookingMessage);

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", booking.getUser().getId());
        payload.put("checkIn", booking.getCheckIn());
        payload.put("checkOut", booking.getCheckOut());

        Event bookingEvent = new Event();
        bookingEvent.setId(UUID.randomUUID());
        bookingEvent.setType("Room-Booking");
        bookingEvent.setTimestamp(Instant.now());
        bookingEvent.setPayload(payload);

        eventService.onMessage(bookingEvent);

        return booking;
    }

    public Booking update(Booking booking) {
        Booking existed = getById(booking.getId());

        BeanUtils.copyNonNullProperties(booking, existed);

        return bookingRepository.save(existed);
    }

    public void deleteById(UUID id) {
        bookingRepository.deleteById(id);
    }
}
