package com.enterpriseshop.order.events;

import com.enterpriseshop.events.OrderPlacedEvent;
import com.enterpriseshop.events.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsPublisher {

    private final KafkaTemplate<String, Object> kafka;

    public OrderEventsPublisher(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    public void orderPlaced(OrderPlacedEvent event) {
        // key by orderId: all events for one order land on the same partition, preserving order
        kafka.send(Topics.ORDER_PLACED, event.orderId(), event);
    }
}
