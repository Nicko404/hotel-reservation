package com.example.hotel_reservation.repository.specification;

import com.example.hotel_reservation.entity.Hotel;
import com.example.hotel_reservation.web.model.hotel.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter hotelFilter) {
        return Specification.allOf(byId(hotelFilter.getId()))
                .and(byName(hotelFilter.getName()))
                .and(byTitle(hotelFilter.getTitle()))
                .and(byCity(hotelFilter.getCity()))
                .and(byAddress(hotelFilter.getAddress()))
                .and(byNumberOfRatings(hotelFilter.getMinNumberOfRatings()))
                .and(byDistanceFromCenter(hotelFilter.getMinDistanceFromCenter(), hotelFilter.getMaxDistanceFromCenter()))
                .and(byRating(hotelFilter.getMinRating()));

    }

    static Specification<Hotel> byId(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) return null;

            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) return null;

            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    static Specification<Hotel> byTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) return null;

            return criteriaBuilder.equal(root.get("title"), title);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) return null;

            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null) return null;

            return criteriaBuilder.equal(root.get("address"), address);
        };
    }

    static Specification<Hotel> byNumberOfRatings(Integer minNumberOfRatings) {
        return (root, query, criteriaBuilder) -> {
            if (minNumberOfRatings == null) return null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfRatings"), minNumberOfRatings);
        };
    }

    static Specification<Hotel> byDistanceFromCenter(Integer minDistanceFromCenter, Integer maxDistanceFromCenter) {
        return (root, query, criteriaBuilder) -> {
            if (minDistanceFromCenter == null && maxDistanceFromCenter == null) return null;
            if (minDistanceFromCenter == null) return criteriaBuilder.lessThanOrEqualTo(root.get("distanceFromCenter"), maxDistanceFromCenter);
            if (maxDistanceFromCenter == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("distanceFromCenter"), minDistanceFromCenter);

            return criteriaBuilder.between(root.get("distanceFromCenter"), minDistanceFromCenter, maxDistanceFromCenter);
        };
    }

    static Specification<Hotel> byRating(Double rating) {
        return (root, query, criteriaBuilder) -> {
            if (rating == null) return null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }
}
