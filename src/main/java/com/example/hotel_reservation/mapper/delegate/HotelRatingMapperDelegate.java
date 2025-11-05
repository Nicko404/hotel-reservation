package com.example.hotel_reservation.mapper.delegate;

import com.example.hotel_reservation.entity.HotelRating;
import com.example.hotel_reservation.entity.HotelRatingKey;
import com.example.hotel_reservation.mapper.HotelRatingMapper;
import com.example.hotel_reservation.service.HotelService;
import com.example.hotel_reservation.service.UserService;
import com.example.hotel_reservation.web.model.hotel.UpsertRatingRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
public abstract class HotelRatingMapperDelegate implements HotelRatingMapper {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserService userService;

    @Override
    public HotelRating requestToRating(UpsertRatingRequest request) {
        HotelRatingKey key = new HotelRatingKey(request.getUserId(), request.getHotelId());
        HotelRating hotelRating = new HotelRating();
        hotelRating.setId(key);
        hotelRating.setHotel(hotelService.getById(request.getHotelId()));
        hotelRating.setUser(userService.getById(request.getUserId()));
        hotelRating.setRating(request.getRating());

        return hotelRating;
    }
}
