package com.example.hotel_reservation.validation;

import com.example.hotel_reservation.web.model.hotel.HotelFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HotelFilterValidValidator implements ConstraintValidator<HotelFilterValid, HotelFilter> {

    @Override
    public boolean isValid(HotelFilter hotelFilter, ConstraintValidatorContext constraintValidatorContext) {
        return hotelFilter.getPageNumber() != null && hotelFilter.getPageSize() != null;
    }
}
