package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.Hotel;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.repository.HotelRepository;
import com.example.hotel_reservation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
