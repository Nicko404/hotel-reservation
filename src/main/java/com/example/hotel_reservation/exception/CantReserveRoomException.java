package com.example.hotel_reservation.exception;

public class CantReserveRoomException extends RuntimeException {
    public CantReserveRoomException(String message) {
        super(message);
    }
}
