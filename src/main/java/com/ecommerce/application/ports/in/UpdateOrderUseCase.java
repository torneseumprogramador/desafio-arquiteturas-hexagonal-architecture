package com.ecommerce.application.ports.in;

import com.ecommerce.domain.model.Order;

import java.util.Map;

public interface UpdateOrderUseCase {
    Order updateOrder(Long id, Long userId, Map<Long, Integer> productQuantities);
} 