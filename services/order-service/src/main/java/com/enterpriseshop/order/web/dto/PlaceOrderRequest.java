package com.enterpriseshop.order.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record PlaceOrderRequest(
        @NotBlank String userId,
        @NotEmpty @Valid List<Line> lines
) {
    public record Line(
            @NotBlank String productId,
            @Min(1) int quantity,
            @NotNull @Positive BigDecimal unitPrice
    ) {}
}
