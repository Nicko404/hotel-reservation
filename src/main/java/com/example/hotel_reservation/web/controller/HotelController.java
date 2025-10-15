package com.example.hotel_reservation.web.controller;

import com.example.hotel_reservation.mapper.HotelMapper;
import com.example.hotel_reservation.mapper.HotelRatingMapper;
import com.example.hotel_reservation.service.HotelService;
import com.example.hotel_reservation.web.model.hotel.HotelListResponse;
import com.example.hotel_reservation.web.model.hotel.HotelResponse;
import com.example.hotel_reservation.web.model.hotel.UpsertHotelRequest;
import com.example.hotel_reservation.web.model.hotel.UpsertRatingRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    private final HotelRatingMapper hotelRatingMapper;

    @GetMapping
    public ResponseEntity<HotelListResponse> getAll() {
        return ResponseEntity.ok(hotelMapper.hotelListToHotelListResponse(hotelService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService.getById(id)));
    }

    @PostMapping("/add-rating")
    public ResponseEntity<HotelResponse> rating(@RequestBody @Valid UpsertRatingRequest request) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService.addRating(hotelRatingMapper.requestToRating(request))));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid UpsertHotelRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(
                        hotelService.create(
                                hotelMapper.requestToHotel(request)
                        )
                ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable UUID id, @RequestBody @Valid UpsertHotelRequest request) {
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(
                        hotelService.update(
                                hotelMapper.requestToHotel(id, request)
                        )
                ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
