package com.example.hotel_reservation.web.model.user;

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
public class UpsertUserRequest {

    private String username;

    private String password;

    private String email;

    @Builder.Default
    private List<String> roles = new ArrayList<>();
}
