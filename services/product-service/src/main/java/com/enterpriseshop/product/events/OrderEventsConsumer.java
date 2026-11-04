package com.enterpriseshop.product.events;

import com.enterpriseshop.events.OrderPlacedEvent;
import com.enterpriseshop.events.StockReservedEvent;
import com.enterpriseshop.events.Topics;
import com.enterpriseshop.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventsConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderEventsConsumer.class);
    private final ProductService products;
    private final StockEventsPublisher publisher;

    public OrderEventsConsumer(ProductService products, StockEventsPublisher publisher) {
        this.products = products; this.publisher = publisher;
    }

    @KafkaListener(topics = Topics.ORDER_PLACED, groupId = "product-service")
    public void onOrderPlaced(OrderPlacedEvent event) {
        log.info("order.placed received: reserving stock for order {}", event.orderId());
        boolean ok = true;
        String reason = "reserved";
        for (OrderPlacedEvent.Line line : event.lines()) {
            if (!products.tryReserve(line.productId(), line.quantity())) {
                ok = false; reason = "insufficient stock for " + line.productId(); break;
            }
        }
        publisher.stockReserved(new StockReservedEvent(event.orderId(), ok, reason, Instant.now()));
    }
}
