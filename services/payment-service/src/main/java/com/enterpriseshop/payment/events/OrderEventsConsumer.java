package com.enterpriseshop.payment.events;

import com.enterpriseshop.events.OrderPlacedEvent;
import com.enterpriseshop.events.PaymentCompletedEvent;
import com.enterpriseshop.events.Topics;
import com.enterpriseshop.payment.domain.Payment;
import com.enterpriseshop.payment.repo.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrderEventsConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderEventsConsumer.class);
    private final PaymentRepository repo;
    private final PaymentEventsPublisher publisher;

    public OrderEventsConsumer(PaymentRepository repo, PaymentEventsPublisher publisher) {
        this.repo = repo; this.publisher = publisher;
    }

    // Simulated gateway: always approves. Swap in a real PSP call here.
    @KafkaListener(topics = Topics.ORDER_PLACED, groupId = "payment-service")
    @Transactional
    public void onOrderPlaced(OrderPlacedEvent event) {
        log.info("order.placed received: charging {} for order {}", event.total(), event.orderId());
        boolean success = true;
        repo.save(new Payment(UUID.randomUUID().toString(), event.orderId(),
                event.total(), success, Instant.now()));
        publisher.paymentCompleted(new PaymentCompletedEvent(
                event.orderId(), event.userId(), event.total(), success, Instant.now()));
    }
}
