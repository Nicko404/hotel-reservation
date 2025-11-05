package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.Hotel;
import com.example.hotel_reservation.entity.HotelRating;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.repository.HotelRatingRepository;
import com.example.hotel_reservation.repository.HotelRepository;
import com.example.hotel_reservation.repository.UserRepository;
import com.example.hotel_reservation.repository.specification.HotelSpecification;
import com.example.hotel_reservation.utils.BeanUtils;
import com.example.hotel_reservation.web.model.hotel.HotelFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    private final HotelRatingRepository hotelRatingRepository;

    private final UserRepository userRepository;

    public Pair<List<Hotel>, Long> filterBy(HotelFilter hotelFilter) {

        Page<Hotel> all = hotelRepository.findAll(
                HotelSpecification.withFilter(hotelFilter),
                PageRequest.of(hotelFilter.getPageNumber(), hotelFilter.getPageSize()));

        return Pair.of(all.getContent(), all.getTotalElements());
    }

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

    public Hotel addRating(HotelRating hotelRating) {
        Hotel hotel = hotelRating.getHotel();
        Integer numberOfRating = hotel.getNumberOfRatings();
        Double rating;

        if (hotelRatingRepository.existsById(hotelRating.getId())) {
            HotelRating existedHotelRating = hotelRatingRepository.findById(hotelRating.getId()).orElseThrow(
                    () -> new EntityNotFoundException("RatingNoteFound!")
            );

            rating = recalculateRating(
                    hotel.getRating(),
                    Double.valueOf(existedHotelRating.getRating()),
                    Double.valueOf(hotelRating.getRating()),
                    numberOfRating);
        } else {
            rating = calculateRating(hotel.getRating(), Double.valueOf(hotelRating.getRating()), numberOfRating);
            hotel.setNumberOfRatings(++numberOfRating);
        }

        hotel.setRating(rating);

        hotelRatingRepository.save(hotelRating);
        userRepository.save(hotelRating.getUser());

        return hotelRepository.save(hotel);
    }

    private Double calculateRating(Double rating, Double newMark, Integer numberOfRating) {
        double totalRating;

        if (rating == 0 || numberOfRating == 0) {
            rating = newMark;
        } else {
            totalRating = rating * numberOfRating;
            totalRating = totalRating - rating + newMark;
            rating = totalRating / numberOfRating;
        }

        return Math.round(rating * 10) / 10.0;
    }

    private Double recalculateRating(Double rating, Double oldMark, Double newMark, Integer numberOfRating) {
        double totalRating = rating * numberOfRating;
        totalRating = totalRating - oldMark + newMark;
        rating = totalRating / numberOfRating;

        return Math.round(rating * 10) / 10.0;
    }
}
