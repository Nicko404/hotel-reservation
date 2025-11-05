package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.repository.RoomRepository;
import com.example.hotel_reservation.repository.specification.RoomSpecification;
import com.example.hotel_reservation.utils.BeanUtils;
import com.example.hotel_reservation.web.model.room.RoomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> filterBy(RoomFilter roomFilter) {
        return roomRepository.findAll(RoomSpecification.withFilter(roomFilter),
                        PageRequest.of(roomFilter.getPageNumber(), roomFilter.getPageSize()))
                .getContent();
    }

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(UUID id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Room with ID {0} not found!", id)));
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Room room) {
        Room existed = getById(room.getId());

        BeanUtils.copyNonNullProperties(room, existed);

        return roomRepository.save(room);
    }

    public void deleteById(UUID id) {
        roomRepository.deleteById(id);
    }
}
