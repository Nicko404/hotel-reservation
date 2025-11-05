package com.example.hotel_reservation.exception;

public class CantCreateCSVFileException extends RuntimeException {
    public CantCreateCSVFileException(String message) {
        super(message);
    }
}
