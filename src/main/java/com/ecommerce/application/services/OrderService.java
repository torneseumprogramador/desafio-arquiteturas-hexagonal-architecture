package com.ecommerce.application.services;

import com.ecommerce.application.ports.in.CreateOrderUseCase;
import com.ecommerce.application.ports.in.UpdateOrderUseCase;
import com.ecommerce.application.ports.in.DeleteOrderUseCase;
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
public class OrderService implements CreateOrderUseCase, UpdateOrderUseCase, DeleteOrderUseCase {

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

    @Override
    public Order updateOrder(Long id, Long userId, Map<Long, Integer> productQuantities) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        // Verifica se o pedido já foi processado
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new RuntimeException("Não é possível alterar um pedido já processado");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Limpa os produtos atuais
        order.clearProducts();
        
        // Adiciona os novos produtos
        List<Long> productIds = productQuantities.keySet().stream().toList();
        List<Product> products = productRepository.findByIds(productIds);
        
        if (products.size() != productIds.size()) {
            throw new RuntimeException("Alguns produtos não foram encontrados");
        }
        
        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getId());
            OrderProduct orderProduct = new OrderProduct(product, quantity);
            order.addProduct(orderProduct);
        }
        
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        // Verifica se o pedido já foi processado
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new RuntimeException("Não é possível deletar um pedido já processado");
        }
        
        orderRepository.deleteById(id);
    }
} 