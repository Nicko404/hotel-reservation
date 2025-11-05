package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.HotelRating;
import com.example.hotel_reservation.entity.HotelRatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelRatingRepository extends JpaRepository<HotelRating, HotelRatingKey> {
}
