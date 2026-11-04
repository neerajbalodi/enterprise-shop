CREATE TABLE payments (
    id         VARCHAR(36)  PRIMARY KEY,
    order_id   VARCHAR(36)  NOT NULL,
    amount     NUMERIC(12,2) NOT NULL,
    success    BOOLEAN      NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL
);
CREATE INDEX idx_payments_order ON payments (order_id);
