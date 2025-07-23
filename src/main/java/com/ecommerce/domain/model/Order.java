package com.ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private User user;
    private List<OrderProduct> orderProducts;
    private LocalDateTime createdAt;
    private OrderStatus status;

    public Order(User user) {
        this.user = user;
        this.orderProducts = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        validate();
    }

    private void validate() {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
    }

    public void addProduct(OrderProduct orderProduct) {
        if (orderProduct == null) {
            throw new IllegalArgumentException("Item do pedido não pode ser nulo");
        }
        
        if (status != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Não é possível adicionar produtos a um pedido já processado");
        }
        
        orderProducts.add(orderProduct);
    }

    // Método para carregar produtos de um pedido já existente (usado pelo adaptador)
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = new ArrayList<>(orderProducts);
    }

    public void removeProduct(OrderProduct orderProduct) {
        if (status != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Não é possível remover produtos de um pedido já processado");
        }
        orderProducts.remove(orderProduct);
    }

    public BigDecimal getTotalAmount() {
        return orderProducts.stream()
                .map(OrderProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void processOrder() {
        if (user == null) {
            throw new IllegalArgumentException("Pedido não pode ser processado sem um usuário");
        }

        if (orderProducts.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um produto");
        }
        
        if (status != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Pedido já foi processado");
        }
        
        // Processa cada item do pedido (diminui estoque)
        orderProducts.forEach(OrderProduct::processOrder);
        
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancelOrder() {
        if (status == OrderStatus.CONFIRMED) {
            // Se o pedido foi confirmado, devolve o estoque
            orderProducts.forEach(orderProduct -> 
                orderProduct.getProduct().increaseStock(orderProduct.getQuantity())
            );
        }
        this.status = OrderStatus.CANCELLED;
    }

    public int getTotalItems() {
        return orderProducts.stream()
                .mapToInt(OrderProduct::getQuantity)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
} 