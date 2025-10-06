package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.web.model.user.UpsertUserRequest;
import com.example.hotel_reservation.web.model.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    User requestToUser(UUID id, UpsertUserRequest request);

    UserResponse userToResponse(User user);
}
