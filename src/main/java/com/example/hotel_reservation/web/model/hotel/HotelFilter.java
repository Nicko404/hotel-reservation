package com.example.hotel_reservation.web.model.hotel;

import com.example.hotel_reservation.validation.HotelFilterValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@HotelFilterValid
public class HotelFilter {

    private UUID id;

    private String name;

    private String title;

    private String city;

    private String address;

    private Integer minDistanceFromCenter;

    private Integer maxDistanceFromCenter;

    private Integer minNumberOfRatings;

    private Double minRating;

    private Integer pageNumber;

    private Integer pageSize;
}
