package com.enterpriseshop.product.web;

import com.enterpriseshop.product.domain.Product;
import com.enterpriseshop.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<Product> all() { return service.all(); }

    @GetMapping("/{id}")
    public Product byId(@PathVariable String id) { return service.byId(id); }
}
