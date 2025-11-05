package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.event.StoredEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<StoredEvent, String> {
}
