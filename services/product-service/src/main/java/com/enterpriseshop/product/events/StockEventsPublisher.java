package com.enterpriseshop.product.events;

import com.enterpriseshop.events.StockReservedEvent;
import com.enterpriseshop.events.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockEventsPublisher {
    private final KafkaTemplate<String, Object> kafka;
    public StockEventsPublisher(KafkaTemplate<String, Object> kafka) { this.kafka = kafka; }
    public void stockReserved(StockReservedEvent e) { kafka.send(Topics.STOCK_RESERVED, e.orderId(), e); }
}
