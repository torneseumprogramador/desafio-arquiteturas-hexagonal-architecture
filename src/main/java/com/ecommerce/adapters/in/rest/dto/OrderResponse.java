package com.ecommerce.adapters.in.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private UserResponse user;
    private List<OrderProductResponse> orderProducts;
    private LocalDateTime createdAt;
    private String status;
    private BigDecimal totalAmount;
    private Integer totalItems;
} 