package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.mapper.delegate.RoomMapperDelegate;
import com.example.hotel_reservation.web.model.room.RoomListResponse;
import com.example.hotel_reservation.web.model.room.RoomResponse;
import com.example.hotel_reservation.web.model.room.UpsertRoomRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {BookingMapper.class})
public interface RoomMapper {

    Room requestToRoom(UpsertRoomRequest request);

    Room requestToRoom(UUID id, UpsertRoomRequest request);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponse roomToResponse(Room room);

    default RoomListResponse roomListToRoomListResponse(List<Room> rooms) {
        return new RoomListResponse(rooms.stream().map(this::roomToResponse).toList());
    }
}
