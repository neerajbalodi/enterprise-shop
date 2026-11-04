package com.enterpriseshop.events;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCompletedEvent(
        String orderId,
        String userId,
        BigDecimal amount,
        boolean success,
        Instant occurredAt
) {}
