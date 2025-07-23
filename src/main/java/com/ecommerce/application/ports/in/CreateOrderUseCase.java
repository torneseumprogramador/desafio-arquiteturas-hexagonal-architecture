package com.ecommerce.application.ports.in;

import com.ecommerce.domain.model.Order;

import java.util.List;
import java.util.Map;

public interface CreateOrderUseCase {
    Order createOrder(Long userId, Map<Long, Integer> productQuantities);
} 