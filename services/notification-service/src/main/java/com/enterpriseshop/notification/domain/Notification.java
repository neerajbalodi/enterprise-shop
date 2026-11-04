package com.enterpriseshop.notification.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id private String id;
    @Column(nullable = false) private String channel;
    @Column(nullable = false) private String recipient;
    @Column(nullable = false, length = 1000) private String message;
    @Column(name = "created_at", nullable = false) private Instant createdAt;

    protected Notification() {}
    public Notification(String id, String channel, String recipient, String message, Instant createdAt) {
        this.id = id; this.channel = channel; this.recipient = recipient; this.message = message; this.createdAt = createdAt;
    }
    public String getId() { return id; }
    public String getChannel() { return channel; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public Instant getCreatedAt() { return createdAt; }
}
