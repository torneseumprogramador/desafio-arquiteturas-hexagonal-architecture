package com.ecommerce.adapters.in.rest.dto;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

@Data
public class CreateOrderRequest {
    
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    
    @NotNull(message = "Produtos são obrigatórios")
    @Size(min = 1, message = "Pedido deve conter pelo menos um produto")
    private Map<Long, Integer> productQuantities;
} 