package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findAllByUser(User user);


    @Query(value = "SELECT COUNT(*) > 0 FROM bookings b WHERE b.room_id = :room_id AND NOT (b.departure <= :checkIn OR b.check_in >= :departure)", nativeQuery = true)
    Boolean dateIsReserved(@Param("room_id") UUID roomId,
                           @Param("checkIn") Date checkIn,
                           @Param("departure") Date departure);
}
