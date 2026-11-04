CREATE TABLE orders (
    id         VARCHAR(36)  PRIMARY KEY,
    user_id    VARCHAR(36)  NOT NULL,
    status     VARCHAR(20)  NOT NULL,
    total      NUMERIC(12,2) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL
);

CREATE TABLE order_items (
    id         VARCHAR(36)  PRIMARY KEY,
    order_id   VARCHAR(36)  NOT NULL REFERENCES orders(id),
    product_id VARCHAR(36)  NOT NULL,
    quantity   INT          NOT NULL,
    unit_price NUMERIC(12,2) NOT NULL
);

CREATE INDEX idx_order_items_order ON order_items (order_id);
CREATE INDEX idx_orders_user ON orders (user_id);
