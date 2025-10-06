package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private Integer number;

    private BigDecimal cost;

    @Column(name = "max_people_count")
    private Integer maxPeopleCount;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel;
}
