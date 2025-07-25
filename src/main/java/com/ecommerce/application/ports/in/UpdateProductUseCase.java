package com.ecommerce.application.ports.in;

import com.ecommerce.domain.model.Product;

public interface UpdateProductUseCase {
    Product updateProduct(Long id, String name, String description, Double price, Integer stockQuantity);
} 