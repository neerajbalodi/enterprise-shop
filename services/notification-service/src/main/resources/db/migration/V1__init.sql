CREATE TABLE notifications (
    id         VARCHAR(36)  PRIMARY KEY,
    channel    VARCHAR(20)  NOT NULL,
    recipient  VARCHAR(255) NOT NULL,
    message    VARCHAR(1000) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL
);
CREATE INDEX idx_notifications_created ON notifications (created_at DESC);
