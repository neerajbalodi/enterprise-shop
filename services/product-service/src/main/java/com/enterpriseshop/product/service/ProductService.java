package com.enterpriseshop.product.service;

import com.enterpriseshop.product.domain.Product;
import com.enterpriseshop.product.repo.ProductRepository;
import com.enterpriseshop.web.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<Product> all() { return repo.findAll(); }

    @Cacheable(cacheNames = "products", key = "#id")
    @Transactional(readOnly = true)
    public Product byId(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("product not found: " + id));
    }

    @Transactional
    public boolean tryReserve(String productId, int qty) {
        Product p = repo.findById(productId).orElse(null);
        if (p == null) return false;
        try { p.reserve(qty); repo.save(p); return true; }
        catch (IllegalStateException e) { return false; }
    }
}
