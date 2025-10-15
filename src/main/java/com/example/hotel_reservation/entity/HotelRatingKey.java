package com.example.hotel_reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRatingKey {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "hotel_id", nullable = false)
    private UUID hotelId;
}
