package com.example.hotel_reservation.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "events")
public class StoredEvent {

    @Id
    private String id;

    private String type;

    private Instant timestamp;

    private Map<String, Object> payload;
}
