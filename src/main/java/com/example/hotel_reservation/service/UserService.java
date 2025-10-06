package com.example.hotel_reservation.service;

import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.exception.EntityAlreadyExistsException;
import com.example.hotel_reservation.exception.EntityNotFoundException;
import com.example.hotel_reservation.repository.UserRepository;
import com.example.hotel_reservation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
        return userRepository.save(user);
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
