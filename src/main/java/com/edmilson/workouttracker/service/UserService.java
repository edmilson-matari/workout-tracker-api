package com.edmilson.workouttracker.service;

import com.edmilson.workouttracker.dto.RegisterRequest;
import com.edmilson.workouttracker.entity.User;
import com.edmilson.workouttracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()));
        return userRepository.save(user);
    }

}
