package com.enterpriseshop.events;

import java.time.Instant;

public record UserRegisteredEvent(
        String userId,
        String email,
        Instant occurredAt
) {}
