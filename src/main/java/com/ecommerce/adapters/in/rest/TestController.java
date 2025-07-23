package com.ecommerce.adapters.in.rest;

import com.ecommerce.application.ports.out.OrderRepository;
import com.ecommerce.application.ports.out.ProductRepository;
import com.ecommerce.application.ports.out.UserRepository;
import com.ecommerce.domain.model.Order;
import com.ecommerce.domain.model.OrderProduct;
import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/users")
    public String testUsers() {
        try {
            long count = userRepository.findAll().size();
            return "Users count: " + count;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/products")
    public String testProducts() {
        try {
            long count = productRepository.findAll().size();
            return "Products count: " + count;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/orders")
    public String testOrders() {
        try {
            long count = orderRepository.findAll().size();
            return "Orders count: " + count;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/create-order")
    public String testCreateOrder() {
        try {
            User user = userRepository.findById(1L).orElse(null);
            if (user == null) {
                return "User not found";
            }

            Product product = productRepository.findById(1L).orElse(null);
            if (product == null) {
                return "Product not found";
            }

            Order order = new Order(user);
            OrderProduct orderProduct = new OrderProduct(product, 1);
            order.addProduct(orderProduct);

            // Não processa o pedido ainda, apenas salva
            Order savedOrder = orderRepository.save(order);
            return "Order created with ID: " + savedOrder.getId();
        } catch (Exception e) {
            return "Error: " + e.getMessage() + " - " + e.getClass().getSimpleName();
        }
    }
} 