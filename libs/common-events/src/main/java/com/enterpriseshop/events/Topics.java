package com.enterpriseshop.events;

/** Single source of truth for Kafka topic names across all services. */
public final class Topics {
    private Topics() {}

    public static final String USER_REGISTERED   = "user.registered";
    public static final String ORDER_PLACED      = "order.placed";
    public static final String STOCK_RESERVED    = "stock.reserved";
    public static final String PAYMENT_COMPLETED = "payment.completed";
}
