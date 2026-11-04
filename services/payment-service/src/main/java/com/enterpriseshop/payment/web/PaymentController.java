package com.enterpriseshop.payment.web;

import com.enterpriseshop.payment.domain.Payment;
import com.enterpriseshop.payment.repo.PaymentRepository;
import com.enterpriseshop.web.NotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentRepository repo;
    public PaymentController(PaymentRepository repo) { this.repo = repo; }

    @GetMapping("/{id}")
    public Payment byId(@PathVariable String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("payment not found: " + id));
    }
}
