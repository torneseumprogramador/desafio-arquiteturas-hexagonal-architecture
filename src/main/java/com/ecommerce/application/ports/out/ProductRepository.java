package com.ecommerce.application.ports.out;

import com.ecommerce.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
    List<Product> findByIds(List<Long> ids);
} 