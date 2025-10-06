package com.example.hotel_reservation.web.model.room;

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
public class RoomListResponse {

    @Builder.Default
    private List<RoomResponse> rooms = new ArrayList<>();
}
