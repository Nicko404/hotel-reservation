package com.example.hotel_reservation.web.model.room;

import com.example.hotel_reservation.validation.RoomFilterValid;
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
@RoomFilterValid
public class RoomFilter {

    private UUID id;

    private String name;

    private BigDecimal minCost;

    private BigDecimal maxCost;

    private Integer maxPeopleCount;

    private String checkIn;

    private String departure;

    private UUID hotelId;

    private Integer pageNumber;

    private Integer pageSize;
}
