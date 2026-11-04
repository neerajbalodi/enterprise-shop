package com.enterpriseshop.order.events;

import com.enterpriseshop.events.PaymentCompletedEvent;
import com.enterpriseshop.events.Topics;
import com.enterpriseshop.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventsConsumer.class);
    private final OrderService orders;

    public PaymentEventsConsumer(OrderService orders) {
        this.orders = orders;
    }

    @KafkaListener(topics = Topics.PAYMENT_COMPLETED, groupId = "order-service")
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        log.info("payment.completed received for order {} success={}", event.orderId(), event.success());
        orders.applyPaymentResult(event.orderId(), event.success());
    }
}
