package com.enterpriseshop.order.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();

    protected Order() {}

    public Order(String id, String userId, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.status = OrderStatus.PENDING;
        this.createdAt = createdAt;
        this.total = BigDecimal.ZERO;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        total = total.add(item.lineTotal());
    }

    public void markPaid()   { this.status = OrderStatus.PAID; }
    public void markFailed() { this.status = OrderStatus.FAILED; }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotal() { return total; }
    public Instant getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
}
