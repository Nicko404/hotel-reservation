package com.example.hotel_reservation.exception;

public class WrongDateFormatException extends RuntimeException {

    public WrongDateFormatException(String message) {
        super(message);
    }
}
