package com.ecommerce.adapters.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class UpdateOrderRequest {
    
    @NotNull(message = "ID do usuário é obrigatório")
    @Min(value = 1, message = "ID do usuário deve ser maior que zero")
    private Long userId;
    
    @NotNull(message = "Quantidades dos produtos são obrigatórias")
    private Map<Long, Integer> productQuantities;
} 