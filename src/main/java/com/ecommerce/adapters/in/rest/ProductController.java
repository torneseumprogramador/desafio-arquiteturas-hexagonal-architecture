package com.ecommerce.adapters.in.rest;

import com.ecommerce.adapters.in.rest.dto.CreateProductRequest;
import com.ecommerce.adapters.in.rest.dto.ProductResponse;
import com.ecommerce.application.ports.in.CreateProductUseCase;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = createProductUseCase.createProduct(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity()
        );

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = products.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        return response;
    }
} 