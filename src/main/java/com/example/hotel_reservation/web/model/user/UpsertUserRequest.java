package com.example.hotel_reservation.web.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "The user's username must be filled!")
    @Size(min = 2, max = 25, message = "The user's name must be between {min} and {max}!")
    private String username;

    @NotBlank(message = "The user's password must be filled!")
    @Size(min = 7, max = 25, message = "The user's password must be between {min} and {max}!")
    private String password;

    @NotBlank(message = "The user's email must be filled!")
    private String email;

    @Builder.Default
    @NotNull(message = "The user's role must be filled!")
    private List<String> roles = new ArrayList<>();
}
