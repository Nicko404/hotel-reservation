package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String title;

    private String city;

    private String address;

    @Column(name = "distance_from_center")
    private Integer distanceFromCenter;

    @Builder.Default
    private Double rating = 0d;

    @Column(name = "number_of_ratings")
    @Builder.Default
    private Integer numberOfRatings = 0;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<HotelRating> ratings = new HashSet<>();
}
