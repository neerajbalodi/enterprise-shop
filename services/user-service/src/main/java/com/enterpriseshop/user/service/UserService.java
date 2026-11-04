package com.enterpriseshop.user.service;

import com.enterpriseshop.events.UserRegisteredEvent;
import com.enterpriseshop.user.domain.User;
import com.enterpriseshop.user.events.UserEventsPublisher;
import com.enterpriseshop.user.repo.UserRepository;
import com.enterpriseshop.user.security.JwtService;
import com.enterpriseshop.user.web.dto.AuthResponse;
import com.enterpriseshop.user.web.dto.LoginRequest;
import com.enterpriseshop.user.web.dto.RegisterRequest;
import com.enterpriseshop.web.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;
    private final JwtService jwt;
    private final UserEventsPublisher events;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo, JwtService jwt, UserEventsPublisher events) {
        this.repo = repo;
        this.jwt = jwt;
        this.events = events;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("email already registered");
        }
        User user = new User(
                UUID.randomUUID().toString(),
                req.email(),
                encoder.encode(req.password()),
                req.displayName(),
                Instant.now());
        repo.save(user);

        events.userRegistered(new UserRegisteredEvent(user.getId(), user.getEmail(), Instant.now()));

        return new AuthResponse(jwt.issue(user.getId(), user.getEmail()), user.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        User user = repo.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid credentials");
        }
        return new AuthResponse(jwt.issue(user.getId(), user.getEmail()), user.getId(), user.getEmail());
    }

    /** Cached in Redis under the "users" cache; evicted by TTL (see application.yml). */
    @Cacheable(cacheNames = "users", key = "#id")
    @Transactional(readOnly = true)
    public User getById(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("user not found: " + id));
    }
}
