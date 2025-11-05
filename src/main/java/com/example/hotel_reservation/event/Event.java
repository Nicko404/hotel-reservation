package com.example.hotel_reservation.event;

import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
public class Event {

    private UUID id;

    private String type;

    private Instant timestamp;

    private Map<String, Object> payload;
}
