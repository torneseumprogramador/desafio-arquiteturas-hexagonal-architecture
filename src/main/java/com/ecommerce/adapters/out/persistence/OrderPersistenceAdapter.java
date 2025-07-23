package com.ecommerce.adapters.out.persistence;

import com.ecommerce.adapters.out.persistence.entity.OrderEntity;
import com.ecommerce.adapters.out.persistence.entity.OrderProductEntity;
import com.ecommerce.adapters.out.persistence.entity.ProductEntity;
import com.ecommerce.adapters.out.persistence.entity.UserEntity;
import com.ecommerce.application.ports.out.OrderRepository;
import com.ecommerce.domain.model.Order;
import com.ecommerce.domain.model.OrderProduct;
import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = toOrderEntity(order);
        OrderEntity savedEntity = jpaOrderRepository.save(orderEntity);
        return toOrder(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderRepository.findById(id)
                .map(this::toOrder);
    }

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll().stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return jpaOrderRepository.findByUserId(userId).stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaOrderRepository.deleteById(id);
    }

    private OrderEntity toOrderEntity(Order order) {
        UserEntity userEntity = jpaUserRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setUser(userEntity);
        orderEntity.setCreatedAt(order.getCreatedAt());
        orderEntity.setStatus(OrderEntity.OrderStatus.valueOf(order.getStatus().name()));

        // Cria os OrderProductEntity
        List<OrderProductEntity> orderProductEntities = order.getOrderProducts().stream()
                .map(orderProduct -> toOrderProductEntity(orderProduct, orderEntity))
                .collect(Collectors.toList());

        orderEntity.setOrderProducts(orderProductEntities);
        return orderEntity;
    }

    private OrderProductEntity toOrderProductEntity(OrderProduct orderProduct, OrderEntity orderEntity) {
        ProductEntity productEntity = jpaProductRepository.findById(orderProduct.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        return new OrderProductEntity(
                orderProduct.getId(),
                orderEntity,
                productEntity,
                orderProduct.getQuantity(),
                orderProduct.getUnitPrice()
        );
    }

    private Order toOrder(OrderEntity orderEntity) {
        User user = toUser(orderEntity.getUser());
        Order order = new Order(user);
        order.setId(orderEntity.getId());
        order.setCreatedAt(orderEntity.getCreatedAt());
        order.setStatus(Order.OrderStatus.valueOf(orderEntity.getStatus().name()));

        // Carrega os produtos do pedido
        List<OrderProduct> orderProducts = orderEntity.getOrderProducts().stream()
                .map(this::toOrderProduct)
                .collect(Collectors.toList());

        order.setOrderProducts(orderProducts);
        return order;
    }

    private User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword()
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

    private OrderProduct toOrderProduct(OrderProductEntity orderProductEntity) {
        Product product = toProduct(orderProductEntity.getProduct());
        return new OrderProduct(
                orderProductEntity.getId(),
                product,
                orderProductEntity.getQuantity(),
                orderProductEntity.getUnitPrice()
        );
    }
} 