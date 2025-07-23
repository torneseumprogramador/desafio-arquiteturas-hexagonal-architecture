package com.ecommerce.adapters.in.rest;

import com.ecommerce.adapters.in.rest.dto.CreateOrderRequest;
import com.ecommerce.adapters.in.rest.dto.OrderProductResponse;
import com.ecommerce.adapters.in.rest.dto.OrderResponse;
import com.ecommerce.adapters.in.rest.dto.ProductResponse;
import com.ecommerce.adapters.in.rest.dto.UserResponse;
import com.ecommerce.application.ports.in.CreateOrderUseCase;
import com.ecommerce.domain.model.Order;
import com.ecommerce.domain.model.OrderProduct;
import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = createOrderUseCase.createOrder(
                request.getUserId(),
                request.getProductQuantities()
        );

        log.info("Order created: {}", order);

        OrderResponse response = toOrderResponse(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private OrderResponse toOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUser(toUserResponse(order.getUser()));
        response.setOrderProducts(toOrderProductResponses(order.getOrderProducts()));
        response.setCreatedAt(order.getCreatedAt());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setTotalItems(order.getTotalItems());
        return response;
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }

    private List<OrderProductResponse> toOrderProductResponses(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(this::toOrderProductResponse)
                .collect(Collectors.toList());
    }

    private OrderProductResponse toOrderProductResponse(OrderProduct orderProduct) {
        OrderProductResponse response = new OrderProductResponse();
        response.setId(orderProduct.getId());
        response.setProduct(toProductResponse(orderProduct.getProduct()));
        response.setQuantity(orderProduct.getQuantity());
        response.setUnitPrice(orderProduct.getUnitPrice());
        response.setTotalPrice(orderProduct.getTotalPrice());
        return response;
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