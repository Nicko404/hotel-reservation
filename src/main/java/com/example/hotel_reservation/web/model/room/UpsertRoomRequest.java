package com.example.hotel_reservation.web.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertRoomRequest {

    private String name;

    private String description;

    private Integer number;

    private BigDecimal cost;

    private Integer maxPeopleCount;

    private UUID hotelId;
}
