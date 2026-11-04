package com.enterpriseshop.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderPlacedEvent(
        String orderId,
        String userId,
        List<Line> lines,
        BigDecimal total,
        Instant occurredAt
) {
    public record Line(String productId, int quantity, BigDecimal unitPrice) {}
}
