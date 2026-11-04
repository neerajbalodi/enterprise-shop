CREATE TABLE products (
    id    VARCHAR(36)  PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    stock INT          NOT NULL
);

INSERT INTO products (id, name, price, stock) VALUES
    ('p-1001', 'Mechanical Keyboard', 89.99, 50),
    ('p-1002', 'Wireless Mouse',      29.99, 120),
    ('p-1003', '27-inch Monitor',     199.99, 30),
    ('p-1004', 'USB-C Hub',           39.99, 200);
