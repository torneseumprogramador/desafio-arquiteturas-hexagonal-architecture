package com.ecommerce.application.ports.in;

import com.ecommerce.domain.model.Product;

import java.math.BigDecimal;

public interface CreateProductUseCase {
    Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity);
} 