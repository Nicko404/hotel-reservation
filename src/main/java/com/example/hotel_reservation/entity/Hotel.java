package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private Integer rating;

    @Column(name = "number_of_ratings")
    private Integer numberOfRatings;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();
}
