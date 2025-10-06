package com.example.hotel_reservation.mapper.delegate;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.exception.WrongDateFormatException;
import com.example.hotel_reservation.mapper.BookingMapper;
import com.example.hotel_reservation.service.RoomService;
import com.example.hotel_reservation.service.UserService;
import com.example.hotel_reservation.web.model.booking.UpsertBookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public abstract class BookingMapperDelegate implements BookingMapper {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Override
    public Booking requestToBooking(UpsertBookingRequest request) {
        try {
            return Booking.builder()
                    .checkIn(formatter.parse(request.getCheckIn()))
                    .departure(formatter.parse(request.getDeparture()))
                    .room(roomService.getById(request.getRoomId()))
                    .user(userService.getById(request.getUserId()))
                    .build();
        } catch (ParseException ex) {
            throw new WrongDateFormatException("The check in and departure fields must match the pattern 'yyyy-MM-dd HH:mm:ss'!");
        }
    }
}
