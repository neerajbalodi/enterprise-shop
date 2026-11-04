package com.enterpriseshop.events;

import java.time.Instant;

public record StockReservedEvent(
        String orderId,
        boolean success,
        String reason,
        Instant occurredAt
) {}
