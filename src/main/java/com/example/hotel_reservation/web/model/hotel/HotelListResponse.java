package com.example.hotel_reservation.web.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelListResponse {

    @Builder.Default
    private List<HotelResponse> hotels = new ArrayList<>();
}
