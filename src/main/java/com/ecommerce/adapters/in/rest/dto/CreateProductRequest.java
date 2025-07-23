package com.ecommerce.adapters.in.rest.dto;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    
    @NotBlank(message = "Nome do produto é obrigatório")
    private String name;
    
    @NotBlank(message = "Descrição do produto é obrigatória")
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal price;
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    private Integer stockQuantity;
} 