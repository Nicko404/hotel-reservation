package com.example.hotel_reservation.web.controller;

import com.example.hotel_reservation.mapper.HotelMapper;
import com.example.hotel_reservation.service.HotelService;
import com.example.hotel_reservation.web.model.hotel.HotelListResponse;
import com.example.hotel_reservation.web.model.hotel.HotelResponse;
import com.example.hotel_reservation.web.model.hotel.UpsertHotelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<HotelListResponse> getAll() {
        return ResponseEntity.ok(hotelMapper.hotelListToHotelListResponse(hotelService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody UpsertHotelRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(
                        hotelService.create(
                                hotelMapper.requestToHotel(request)
                        )
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable UUID id, @RequestBody UpsertHotelRequest request) {
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(
                        hotelService.update(
                                hotelMapper.requestToHotel(id, request)
                        )
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
