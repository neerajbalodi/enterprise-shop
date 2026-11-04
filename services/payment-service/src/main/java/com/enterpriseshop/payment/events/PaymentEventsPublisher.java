package com.enterpriseshop.payment.events;

import com.enterpriseshop.events.PaymentCompletedEvent;
import com.enterpriseshop.events.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsPublisher {
    private final KafkaTemplate<String, Object> kafka;
    public PaymentEventsPublisher(KafkaTemplate<String, Object> kafka) { this.kafka = kafka; }
    public void paymentCompleted(PaymentCompletedEvent e) { kafka.send(Topics.PAYMENT_COMPLETED, e.orderId(), e); }
}
