package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findAllByUser(User user);
}
