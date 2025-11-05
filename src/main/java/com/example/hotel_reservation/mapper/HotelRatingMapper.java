package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.entity.HotelRating;
import com.example.hotel_reservation.mapper.delegate.HotelRatingMapperDelegate;
import com.example.hotel_reservation.web.model.hotel.UpsertRatingRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(HotelRatingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelRatingMapper {

    HotelRating requestToRating(UpsertRatingRequest request);
}
