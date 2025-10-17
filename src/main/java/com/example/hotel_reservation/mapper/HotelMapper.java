package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.entity.Hotel;
import com.example.hotel_reservation.web.model.hotel.HotelFilterByResponse;
import com.example.hotel_reservation.web.model.hotel.HotelListResponse;
import com.example.hotel_reservation.web.model.hotel.HotelResponse;
import com.example.hotel_reservation.web.model.hotel.UpsertHotelRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(UpsertHotelRequest request);

    Hotel requestToHotel(UUID id, UpsertHotelRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    HotelFilterByResponse hotelListToHotelFilterByResponse(List<Hotel> hotels, Long resultCount);

    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels) {
        return new HotelListResponse(hotels.stream().map(this::hotelToResponse).toList());
    }
}
