package com.enterpriseshop.order.web.dto;

import com.enterpriseshop.order.domain.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        String id,
        String userId,
        String status,
        BigDecimal total,
        Instant createdAt,
        List<Item> items
) {
    public record Item(String productId, int quantity, BigDecimal unitPrice) {}

    public static OrderResponse from(Order o) {
        List<Item> items = o.getItems().stream()
                .map(i -> new Item(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .toList();
        return new OrderResponse(o.getId(), o.getUserId(), o.getStatus().name(),
                o.getTotal(), o.getCreatedAt(), items);
    }
}
