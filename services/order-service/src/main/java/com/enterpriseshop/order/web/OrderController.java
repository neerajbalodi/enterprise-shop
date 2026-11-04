package com.enterpriseshop.order.web;

import com.enterpriseshop.order.service.OrderService;
import com.enterpriseshop.order.web.dto.OrderResponse;
import com.enterpriseshop.order.web.dto.PlaceOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse place(@Valid @RequestBody PlaceOrderRequest req,
                               @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        return OrderResponse.from(service.place(req, idempotencyKey));
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable String id) {
        return OrderResponse.from(service.getById(id));
    }
}
