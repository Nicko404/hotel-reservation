package com.example.hotel_reservation.web.model.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "The room's name must be filled!")
    private String name;

    @NotBlank(message = "The room's description must be filled!")
    private String description;

    @NotNull(message = "The room's number must be filled!")
    private Integer number;

    @NotNull(message = "The cost of room must be filled!")
    private BigDecimal cost;

    @NotNull(message = "The max people count per room must be filled!")
    private Integer maxPeopleCount;

    @NotNull(message = "The hotel ID must be filled!")
    private UUID hotelId;
}
