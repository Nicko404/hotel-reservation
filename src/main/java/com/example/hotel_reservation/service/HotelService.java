package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.Hotel;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.repository.HotelRepository;
import com.example.hotel_reservation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    public Hotel getById(UUID id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Hotel with ID {0} not found!", id)
        ));
    }

    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel update(Hotel hotel) {
        Hotel existed = getById(hotel.getId());

        BeanUtils.copyNonNullProperties(hotel, existed);

        return hotelRepository.save(existed);
    }

    public void deleteById(UUID id) {
        hotelRepository.deleteById(id);
    }

    public Hotel addRating(UUID hotelId, Double newMark) {
        Hotel hotel = getById(hotelId);

        Integer numberOfRating = hotel.getNumberOfRatings();

        Double rating = calculateRating(hotel.getRating(), newMark, numberOfRating);

        hotel.setRating(rating);
        hotel.setNumberOfRatings(++numberOfRating);

        return hotelRepository.save(hotel);
    }

    private Double calculateRating(Double rating, Double newMark, Integer numberOfRating) {
        Double totalRating = null;

        if (rating == 0 || numberOfRating == 0) {
            rating = newMark;
        } else {
            totalRating = rating * numberOfRating;
            totalRating = totalRating - rating + newMark;
            rating = totalRating / numberOfRating;
        }

        return Math.round(rating * 10) / 10.0;
    }
}
