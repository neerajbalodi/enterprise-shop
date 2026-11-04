package com.enterpriseshop.user.web.dto;

public record AuthResponse(String token, String userId, String email) {}
