package com.example.hotel_reservation.web.model.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertBookingRequest {

    private String checkIn;

    private String departure;

    private UUID roomId;

    private UUID userId;
}
