package com.enterpriseshop.product.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int stock;

    protected Product() {}
    public Product(String id, String name, BigDecimal price, int stock) {
        this.id = id; this.name = name; this.price = price; this.stock = stock;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
    public void reserve(int qty) {
        if (qty > stock) throw new IllegalStateException("insufficient stock for " + id);
        this.stock -= qty;
    }
}
