package com.ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        validate();
    }

    private void validate() {
        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        if (!product.hasStock(quantity)) {
            throw new IllegalArgumentException("Quantidade solicitada não disponível em estoque");
        }
    }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void processOrder() {
        product.decreaseStock(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
} 