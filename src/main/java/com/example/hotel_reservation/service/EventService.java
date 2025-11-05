package com.example.hotel_reservation.service;

import com.example.hotel_reservation.event.Event;
import com.example.hotel_reservation.event.StoredEvent;
import com.example.hotel_reservation.exception.CantCreateCSVFileException;
import com.example.hotel_reservation.repository.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper;

    @Value("${app.event.collection-name}")
    private String collectionName;


    public void onMessage(Event event) {
        StoredEvent storedEvent = new StoredEvent();
        storedEvent.setId(event.getId().toString());
        storedEvent.setType(event.getType());
        storedEvent.setTimestamp(event.getTimestamp());
        storedEvent.setPayload(event.getPayload());

        try {
            String s = objectMapper.writeValueAsString(storedEvent);
            mongoTemplate.save(Document.parse(s), collectionName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource exportCsvFile() {
        List<StoredEvent> events = eventRepository.findAll(Sort.by("timestamp"));

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("EventId", "Type", "Timestamp", "UserId", "CheckIn", "CheckOut")
                .build();

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                CSVPrinter csvPrinter = new CSVPrinter(outputStreamWriter, csvFormat)
        ) {
            for (StoredEvent event : events) {
                Map<String, Object> payload = event.getPayload();
                csvPrinter.printRecord(
                        event.getId(),
                        event.getType(),
                        event.getTimestamp(),
                        payload.get("userId"),
                        payload.get("checkIn"),
                        payload.get("checkOut")
                );
            }

            csvPrinter.flush();

            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new CantCreateCSVFileException("Can't create CSV statistic file. The Reason is: " + e.getLocalizedMessage());
        }
    }
}
