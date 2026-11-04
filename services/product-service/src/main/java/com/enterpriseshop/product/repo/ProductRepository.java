package com.enterpriseshop.product.repo;

import com.enterpriseshop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {}
