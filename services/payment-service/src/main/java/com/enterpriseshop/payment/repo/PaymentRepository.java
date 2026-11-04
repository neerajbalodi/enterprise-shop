package com.enterpriseshop.payment.repo;

import com.enterpriseshop.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {}
