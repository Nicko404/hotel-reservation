package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.event.Event;
import com.example.hotel_reservation.exception.EntityAlreadyExistsException;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.kafka.KafkaMessage;
import com.example.hotel_reservation.kafka.UserRegistrationMessage;
import com.example.hotel_reservation.repository.UserRepository;
import com.example.hotel_reservation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    private final EventService eventService;

    @Value("${app.kafka.topics.user-register}")
    private String userTopic;


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with ID {0} not found!", id)));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with username {0} not found!", username)));
    }

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername())) {
            throw new EntityAlreadyExistsException(
                    MessageFormat.format(
                            "User with username {0} or email {1} already exists!",
                            user.getUsername(),
                            user.getEmail()
                    )
            );
        }
        user.getRoles().forEach(role -> role.setUser(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        UserRegistrationMessage registrationMessage = new UserRegistrationMessage();
        registrationMessage.setUserId(savedUser.getId());

        kafkaTemplate.send(userTopic, registrationMessage);

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("userId", savedUser.getId());

        Event userRegistrationEvent = new Event();
        userRegistrationEvent.setId(UUID.randomUUID());
        userRegistrationEvent.setType("User-Registration");
        userRegistrationEvent.setTimestamp(Instant.now());
        userRegistrationEvent.setPayload(payload);

        eventService.onMessage(userRegistrationEvent);

        return savedUser;
    }

    public User update(User user) {
        if (alreadyExists(user.getUsername(), user.getEmail())) {
            throw new EntityAlreadyExistsException(MessageFormat.format(
                    "Username {0} or email {1} already occupied!",
                    user.getUsername(),
                    user.getEmail())
            );
        }
        User existed = getById(user.getId());

        BeanUtils.copyNonNullProperties(user, existed);

        return userRepository.save(existed);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    private boolean alreadyExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }
}
