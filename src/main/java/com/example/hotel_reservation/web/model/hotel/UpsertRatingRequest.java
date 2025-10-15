package com.example.hotel_reservation.web.model.hotel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertRatingRequest {

    @NotNull(message = "The user ID must be filled!")
    private UUID userId;

    @NotNull(message = "The hotel ID must be filled!")
    private UUID hotelId;

    @NotNull(message = "The rating must be filled!")
    @Min(value = 1, message = "The rating must be greater or equal 1!")
    @Max(value = 5, message = "The rating must be less or equal 5!")
    private Integer rating;
}
