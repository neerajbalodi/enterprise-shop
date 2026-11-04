package com.enterpriseshop.user.web;

import com.enterpriseshop.user.service.UserService;
import com.enterpriseshop.user.web.dto.AuthResponse;
import com.enterpriseshop.user.web.dto.LoginRequest;
import com.enterpriseshop.user.web.dto.RegisterRequest;
import com.enterpriseshop.user.web.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/api/auth/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return service.login(req);
    }

    @GetMapping("/api/users/{id}")
    public UserResponse getById(@PathVariable String id) {
        return UserResponse.from(service.getById(id));
    }
}
