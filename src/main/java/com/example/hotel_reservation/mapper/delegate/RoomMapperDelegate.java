package com.example.hotel_reservation.mapper.delegate;

import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.mapper.RoomMapper;
import com.example.hotel_reservation.service.HotelService;
import com.example.hotel_reservation.web.model.room.UpsertRoomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class RoomMapperDelegate implements RoomMapper {

    @Autowired
    private HotelService hotelService;

    @Override
    public Room requestToRoom(UpsertRoomRequest request) {
        return Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .number(request.getNumber())
                .cost(request.getCost())
                .maxPeopleCount(request.getMaxPeopleCount())
                .hotel(hotelService.getById(request.getHotelId()))
                .build();
    }
}
