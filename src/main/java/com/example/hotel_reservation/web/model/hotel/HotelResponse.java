package com.example.hotel_reservation.web.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse {

    private UUID id;

    private String name;

    private String title;

    private String city;

    private String address;

    private Integer distanceFromCenter;

    private Double rating;

    private Integer numberOfRatings;
}
