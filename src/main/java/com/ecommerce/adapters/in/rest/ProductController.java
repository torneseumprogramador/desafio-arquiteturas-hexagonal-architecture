package com.ecommerce.adapters.in.rest;

import com.ecommerce.adapters.in.rest.dto.CreateProductRequest;
import com.ecommerce.adapters.in.rest.dto.UpdateProductRequest;
import com.ecommerce.adapters.in.rest.dto.ProductResponse;
import com.ecommerce.application.ports.in.CreateProductUseCase;
import com.ecommerce.application.ports.in.UpdateProductUseCase;
import com.ecommerce.application.ports.in.DeleteProductUseCase;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.domain.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ProductRepository productRepository;

    @PostMapping
    @Operation(summary = "Criar Produto", description = "Cria um novo produto no sistema")
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

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Produto", description = "Atualiza um produto existente no sistema")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        
        Product product = updateProductUseCase.updateProduct(
                id,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity()
        );

        return ResponseEntity.ok(toProductResponse(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Produto", description = "Remove um produto do sistema")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar Produtos", description = "Retorna todos os produtos cadastrados no sistema")
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