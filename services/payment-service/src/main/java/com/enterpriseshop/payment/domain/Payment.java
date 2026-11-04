package com.enterpriseshop.payment.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {
    @Id private String id;
    @Column(name = "order_id", nullable = false) private String orderId;
    @Column(nullable = false) private BigDecimal amount;
    @Column(nullable = false) private boolean success;
    @Column(name = "created_at", nullable = false) private Instant createdAt;

    protected Payment() {}
    public Payment(String id, String orderId, BigDecimal amount, boolean success, Instant createdAt) {
        this.id = id; this.orderId = orderId; this.amount = amount; this.success = success; this.createdAt = createdAt;
    }
    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public boolean isSuccess() { return success; }
    public Instant getCreatedAt() { return createdAt; }
}
