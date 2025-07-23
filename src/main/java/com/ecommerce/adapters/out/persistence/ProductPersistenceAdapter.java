package com.ecommerce.adapters.out.persistence;

import com.ecommerce.adapters.out.persistence.entity.ProductEntity;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = toProductEntity(product);
        ProductEntity savedEntity = jpaProductRepository.save(productEntity);
        return toProduct(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(this::toProduct);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll().stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public List<Product> findByIds(List<Long> ids) {
        return jpaProductRepository.findByIdIn(ids).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private ProductEntity toProductEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    private Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStockQuantity()
        );
    }
} 