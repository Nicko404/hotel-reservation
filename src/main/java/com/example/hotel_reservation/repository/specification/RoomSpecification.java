package com.example.hotel_reservation.repository.specification;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.exception.WrongDateFormatException;
import com.example.hotel_reservation.web.model.room.RoomFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public interface RoomSpecification {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static Specification<Room> withFilter(RoomFilter roomFilter) {
        return Specification.anyOf(byId(roomFilter.getId()))
                .and(byName(roomFilter.getName()))
                .and(byCost(roomFilter.getMinCost(), roomFilter.getMaxCost()))
                .and(byPeopleCount(roomFilter.getMaxPeopleCount()))
                .and(byCheckInAndCheckOut(roomFilter.getCheckIn(), roomFilter.getCheckOut()))
                .and(byHotelId(roomFilter.getHotelId()));
    }

    static Specification<Room> byId(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) return null;

            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    static Specification<Room> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) return null;

            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    static Specification<Room> byCost(BigDecimal minCost, BigDecimal maxCost) {
        return (root, query, criteriaBuilder) -> {
            if (minCost == null && maxCost == null) return null;
            if (minCost == null) return criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
            if (maxCost == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
            return criteriaBuilder.between(root.get("cost"), minCost, maxCost);
        };
    }

    static Specification<Room> byPeopleCount(Integer maxPeopleCount) {
        return (root, query, criteriaBuilder) -> {
            if (maxPeopleCount == null) return null;

            return criteriaBuilder.lessThanOrEqualTo(root.get("maxPeopleCount"), maxPeopleCount);
        };
    }

    static Specification<Room> byCheckInAndCheckOut(String checkIn, String checkOut) {
        return (root, query, criteriaBuilder) -> {
            if (checkIn == null || checkOut == null) return null;

            query.distinct(true);
            Join<Room, Booking> bookingJoin = root.join("bookings", JoinType.LEFT);

            try {
                Date checkInDate = formatter.parse(checkIn);
                Date checkOutDate = formatter.parse(checkOut);

                bookingJoin.on(criteriaBuilder.and(
                        criteriaBuilder.greaterThan(bookingJoin.get("checkOut"), checkInDate),
                        criteriaBuilder.lessThan(bookingJoin.get("checkIn"), checkOutDate)
                ));

                return criteriaBuilder.isNull(bookingJoin.get("id"));

            } catch (ParseException ex) {
                throw new WrongDateFormatException("The check in and checkOut fields must match the pattern 'yyyy-MM-dd HH:mm:ss'!");
            }
        };
    }

    static Specification<Room> byHotelId(UUID hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) return null;

            return criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
        };
    }
}
