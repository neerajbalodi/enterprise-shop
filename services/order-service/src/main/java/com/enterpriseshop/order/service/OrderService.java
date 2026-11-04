package com.enterpriseshop.order.service;

import com.enterpriseshop.events.OrderPlacedEvent;
import com.enterpriseshop.order.domain.Order;
import com.enterpriseshop.order.domain.OrderItem;
import com.enterpriseshop.order.events.OrderEventsPublisher;
import com.enterpriseshop.order.repo.OrderRepository;
import com.enterpriseshop.order.web.dto.PlaceOrderRequest;
import com.enterpriseshop.web.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final OrderEventsPublisher publisher;
    private final IdempotencyService idempotency;

    public OrderService(OrderRepository repo, OrderEventsPublisher publisher, IdempotencyService idempotency) {
        this.repo = repo;
        this.publisher = publisher;
        this.idempotency = idempotency;
    }

    @Transactional
    public Order place(PlaceOrderRequest req, String idempotencyKey) {
        // 1. Idempotency: a retry with the same key returns the original order
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            String existing = idempotency.existingOrderId(idempotencyKey);
            if (existing != null) {
                return getById(existing);
            }
        }

        // 2. Build and persist the order
        Order order = new Order(UUID.randomUUID().toString(), req.userId(), Instant.now());
        for (PlaceOrderRequest.Line line : req.lines()) {
            order.addItem(new OrderItem(UUID.randomUUID().toString(),
                    line.productId(), line.quantity(), line.unitPrice()));
        }
        repo.save(order);

        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            idempotency.reserve(idempotencyKey, order.getId());
        }

        // 3. Emit the event that product-service and payment-service consume
        List<OrderPlacedEvent.Line> lines = order.getItems().stream()
                .map(i -> new OrderPlacedEvent.Line(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .toList();
        publisher.orderPlaced(new OrderPlacedEvent(
                order.getId(), order.getUserId(), lines, order.getTotal(), Instant.now()));

        return order;
    }

    @Transactional(readOnly = true)
    public Order getById(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("order not found: " + id));
    }

    @Transactional
    public void applyPaymentResult(String orderId, boolean success) {
        repo.findById(orderId).ifPresent(order -> {
            if (success) order.markPaid(); else order.markFailed();
            repo.save(order);
        });
    }
}
