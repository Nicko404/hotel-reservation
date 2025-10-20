package com.example.hotel_reservation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomFilterValid {

    String message() default "The check in and departure dates must be filled! If you fill check in or departure dates, then both fields must be filled!";

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
