package com.edmilson.workouttracker.service;

import com.edmilson.workouttracker.dto.RegisterRequest;
import com.edmilson.workouttracker.entity.User;
import com.edmilson.workouttracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()))
        return repository.save(user);
    }

}
