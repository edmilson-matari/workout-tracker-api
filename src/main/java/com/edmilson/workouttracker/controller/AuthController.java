package com.edmilson.workouttracker.controller;

import com.edmilson.workouttracker.dto.RegisterRequest;
import com.edmilson.workouttracker.entity.User;
import com.edmilson.workouttracker.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @GetMapping("/healtAuth")
    public String healtAuth() {
        return "aut endpoint is working";
    }
}
