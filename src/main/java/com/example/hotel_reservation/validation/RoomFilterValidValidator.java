package com.example.hotel_reservation.validation;

import com.example.hotel_reservation.web.model.room.RoomFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoomFilterValidValidator implements ConstraintValidator<RoomFilterValid, RoomFilter> {

    @Override
    public boolean isValid(RoomFilter roomFilter, ConstraintValidatorContext constraintValidatorContext) {
        if (roomFilter.getPageNumber() == null && roomFilter.getPageSize() == null) return false;

        if ((roomFilter.getCheckIn() == null && roomFilter.getCheckOut() != null)
                || (roomFilter.getCheckIn() != null && roomFilter.getCheckOut() == null)) return false;

        return true;
    }
}
