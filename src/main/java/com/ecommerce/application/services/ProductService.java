package com.ecommerce.application.services;

import com.ecommerce.application.ports.in.CreateProductUseCase;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity) {
        Product product = new Product(name, description, price, stockQuantity);
        return productRepository.save(product);
    }
} 