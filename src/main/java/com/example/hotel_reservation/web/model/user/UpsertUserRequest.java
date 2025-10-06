package com.example.hotel_reservation.web.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertUserRequest {

    private String username;

    private String password;

    private String email;

    private String role;
}
