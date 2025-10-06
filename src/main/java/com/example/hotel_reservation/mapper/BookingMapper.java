package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.entity.Booking;
import com.example.hotel_reservation.mapper.delegate.BookingMapperDelegate;
import com.example.hotel_reservation.web.model.booking.BookingListResponse;
import com.example.hotel_reservation.web.model.booking.BookingResponse;
import com.example.hotel_reservation.web.model.booking.UpsertBookingRequest;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mappings({
            @Mapping(target = "checkIn", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "departure", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    Booking requestToBooking(UpsertBookingRequest request);

    Booking requestToBooking(UUID id, UpsertBookingRequest request);

    @Mappings({
            @Mapping(target = "checkIn", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "departure", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(source = "room.id", target = "roomId"),
            @Mapping(source = "user.id", target = "userId")
    })
    BookingResponse bookingToResponse(Booking booking);

    default BookingListResponse bookingListToBookingListResponse(List<Booking> bookings) {
        return new BookingListResponse(bookings.stream().map(this::bookingToResponse).toList());
    }
}
