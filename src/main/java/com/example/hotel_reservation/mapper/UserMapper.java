package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.entity.Role;
import com.example.hotel_reservation.entity.RoleType;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.web.model.user.UpsertUserRequest;
import com.example.hotel_reservation.web.model.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(request.getRoles().stream().map(role -> com.example.hotel_reservation.entity.Role.from(com.example.hotel_reservation.entity.RoleType.valueOf(role))).toList())")
    User requestToUser(UpsertUserRequest request);

    default User requestToUser(UUID id, UpsertUserRequest request) {
        var user = requestToUser(request);
        user.setId(id);

        return user;
    }

    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(com.example.hotel_reservation.entity.Role::getAuthority).map(com.example.hotel_reservation.entity.RoleType::name).toList())")
    UserResponse userToResponse(User user);
}
