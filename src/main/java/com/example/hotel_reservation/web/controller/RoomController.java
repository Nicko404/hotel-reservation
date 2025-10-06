package com.example.hotel_reservation.web.controller;

import com.example.hotel_reservation.mapper.RoomMapper;
import com.example.hotel_reservation.service.RoomService;
import com.example.hotel_reservation.web.model.room.RoomListResponse;
import com.example.hotel_reservation.web.model.room.RoomResponse;
import com.example.hotel_reservation.web.model.room.UpsertRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<RoomListResponse> getAll() {
        return ResponseEntity.ok(roomMapper.roomListToRoomListResponse(roomService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(roomMapper.roomToResponse(roomService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody UpsertRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomToResponse(roomService.create(roomMapper.requestToRoom(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable UUID id, @RequestBody UpsertRoomRequest request) {
        return ResponseEntity.ok(roomMapper.roomToResponse(roomService.update(roomMapper.requestToRoom(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
