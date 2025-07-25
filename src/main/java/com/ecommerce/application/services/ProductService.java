package com.ecommerce.application.services;

import com.ecommerce.application.ports.in.CreateProductUseCase;
import com.ecommerce.application.ports.in.UpdateProductUseCase;
import com.ecommerce.application.ports.in.DeleteProductUseCase;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase, UpdateProductUseCase, DeleteProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity) {
        Product product = new Product(name, description, price, stockQuantity);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, String name, String description, Double price, Integer stockQuantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStockQuantity(stockQuantity);
        
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        
        productRepository.deleteById(id);
    }
} 