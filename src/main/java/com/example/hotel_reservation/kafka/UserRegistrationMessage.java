package com.example.hotel_reservation.kafka;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRegistrationMessage implements KafkaMessage {

    private UUID userId;
}
