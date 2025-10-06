package com.example.hotel_reservation.web.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertHotelRequest {

    private String name;

    private String title;

    private String city;

    private String address;

    private Integer distanceFromCenter;
}
