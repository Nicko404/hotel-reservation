package com.example.hotel_reservation.web.model.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "The check in date must be filled!")
    private String checkIn;

    @NotBlank(message = "The check out date must be filled!")
    private String checkOut;

    @NotNull(message = "The room ID must be filled!")
    private UUID roomId;

    @NotNull(message = "The User ID must be filled!")
    private UUID userId;
}
