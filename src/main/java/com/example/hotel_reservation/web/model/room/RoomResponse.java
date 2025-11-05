package com.example.hotel_reservation.web.model.room;

import com.example.hotel_reservation.web.model.booking.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {

    private UUID id;

    private String name;

    private String description;

    private Integer number;

    private BigDecimal cost;

    private Integer maxPeopleCount;

    @Builder.Default
    private List<BookingResponse> bookings = new ArrayList<>();

    private UUID hotelId;
}
