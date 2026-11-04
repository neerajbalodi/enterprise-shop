package com.enterpriseshop.notification.events;

import com.enterpriseshop.events.PaymentCompletedEvent;
import com.enterpriseshop.events.Topics;
import com.enterpriseshop.events.UserRegisteredEvent;
import com.enterpriseshop.notification.domain.Notification;
import com.enterpriseshop.notification.repo.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/** Fans out notifications. Two listeners, one per event type (resolved via Kafka type headers). */
@Component
public class NotificationConsumers {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumers.class);
    private final NotificationRepository repo;

    public NotificationConsumers(NotificationRepository repo) { this.repo = repo; }

    @KafkaListener(topics = Topics.USER_REGISTERED, groupId = "notification-service")
    public void onUserRegistered(UserRegisteredEvent e) {
        save("email", e.email(), "Welcome to Enterprise Shop!");
    }

    @KafkaListener(topics = Topics.PAYMENT_COMPLETED, groupId = "notification-service")
    public void onPaymentCompleted(PaymentCompletedEvent e) {
        String msg = e.success()
                ? "Payment received for order " + e.orderId() + " (" + e.amount() + ")"
                : "Payment failed for order " + e.orderId();
        save("email", e.userId(), msg);
    }

    private void save(String channel, String recipient, String message) {
        log.info("notify [{}] {} -> {}", channel, recipient, message);
        repo.save(new Notification(UUID.randomUUID().toString(), channel, recipient, message, Instant.now()));
    }
}
