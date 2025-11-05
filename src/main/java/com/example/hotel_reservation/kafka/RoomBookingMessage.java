package com.example.hotel_reservation.kafka;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class RoomBookingMessage implements KafkaMessage {

    private UUID userId;

    private Date checkIn;

    private Date checkOut;
}
