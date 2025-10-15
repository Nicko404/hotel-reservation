package com.example.hotel_reservation.web.controller;

import com.example.hotel_reservation.mapper.BookingMapper;
import com.example.hotel_reservation.service.BookingService;
import com.example.hotel_reservation.web.model.booking.BookingListResponse;
import com.example.hotel_reservation.web.model.booking.BookingResponse;
import com.example.hotel_reservation.web.model.booking.UpsertBookingRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingListResponse> getAll() {
        return ResponseEntity.ok(bookingMapper.bookingListToBookingListResponse(bookingService.getAll()));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody @Valid UpsertBookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingMapper.bookingToResponse(bookingService.create(bookingMapper.requestToBooking(request))));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> update(@PathVariable UUID id, @RequestBody @Valid UpsertBookingRequest request) {
        return ResponseEntity.ok(
                bookingMapper.bookingToResponse(bookingService.update(bookingMapper.requestToBooking(id, request)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        bookingService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
