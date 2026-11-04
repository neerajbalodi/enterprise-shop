package com.enterpriseshop.user.web.dto;

import com.enterpriseshop.user.domain.User;

import java.time.Instant;

public record UserResponse(String id, String email, String displayName, Instant createdAt) {
    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getDisplayName(), u.getCreatedAt());
    }
}
