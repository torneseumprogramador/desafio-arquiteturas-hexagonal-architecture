package com.ecommerce.application.services;

import com.ecommerce.application.ports.in.CreateOrderUseCase;
import com.ecommerce.application.ports.out.OrderRepository;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.application.ports.out.UserRepository;
import com.ecommerce.domain.model.Order;
import com.ecommerce.domain.model.OrderProduct;
import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Order createOrder(Long userId, Map<Long, Integer> productQuantities) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Long> productIds = productQuantities.keySet().stream().toList();
        List<Product> products = productRepository.findByIds(productIds);

        if (products.size() != productIds.size()) {
            throw new IllegalArgumentException("Alguns produtos não foram encontrados");
        }

        Order order = new Order(user);

        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getId());
            OrderProduct orderProduct = new OrderProduct(product, quantity);
            order.addProduct(orderProduct);
        }

        // Processa o pedido (diminui estoque)
        order.processOrder();

        return orderRepository.save(order);
    }
} 