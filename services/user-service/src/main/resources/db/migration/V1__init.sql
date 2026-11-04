CREATE TABLE users (
    id            VARCHAR(36)  PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name  VARCHAR(255),
    created_at    TIMESTAMPTZ  NOT NULL
);

CREATE INDEX idx_users_email ON users (email);
