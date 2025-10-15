package com.example.hotel_reservation.web.model.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertHotelRequest {

    @NotBlank(message = "The hotel's name must be filled!")
    private String name;

    @NotBlank(message = "The hotel's title must be filled!")
    private String title;

    @NotBlank(message = "The hotel's city must be filled!")
    private String city;

    @NotBlank(message = "The address of hotel must be filled!")
    private String address;

    @NotNull(message = "The distance from center must be filled!")
    private Integer distanceFromCenter;
}
