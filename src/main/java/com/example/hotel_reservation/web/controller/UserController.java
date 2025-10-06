package com.example.hotel_reservation.web.controller;

import com.example.hotel_reservation.mapper.UserMapper;
import com.example.hotel_reservation.service.UserService;
import com.example.hotel_reservation.web.model.user.UpsertUserRequest;
import com.example.hotel_reservation.web.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UpsertUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(userService.create(userMapper.requestToUser(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UpsertUserRequest request) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.update(userMapper.requestToUser(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
