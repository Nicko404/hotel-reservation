package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.exception.CantReserveRoomException;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.repository.BookingRepository;
import com.example.hotel_reservation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

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
        if (bookingRepository.dateIsReserved(booking.getRoom().getId(), booking.getCheckIn(), booking.getDeparture()))
            throw new CantReserveRoomException(
                    MessageFormat.format(
                            "The dates from {0} to {1} not available!", booking.getCheckIn(), booking.getDeparture()
                    )
            );
        return bookingRepository.save(booking);
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
