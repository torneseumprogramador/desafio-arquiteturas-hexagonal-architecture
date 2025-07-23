package com.ecommerce.adapters.in.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductResponse {
    private Long id;
    private ProductResponse product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
} 