package com.enterpriseshop.order.repo;

import com.enterpriseshop.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {}
