package com.ecommerce.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Home", description = "Endpoints de informações da API")
public class HomeController {
    
    @GetMapping("/")
    @Operation(summary = "Informações da API", description = "Retorna informações sobre a API, arquitetura e endpoints disponíveis")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "E-commerce Hexagonal Architecture API");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("architecture", "Hexagonal Architecture (Ports and Adapters)");
        response.put("technology", "Java 17 + Spring Boot 3.2.0 + PostgreSQL");
        
        // Endpoints da API
        Map<String, Object> apiEndpoints = new HashMap<>();
        apiEndpoints.put("users", "/api/users");
        apiEndpoints.put("products", "/api/products");
        apiEndpoints.put("orders", "/api/orders");
        response.put("api", apiEndpoints);
        
        // Endpoints de Health Check
        Map<String, Object> healthEndpoints = new HashMap<>();
        healthEndpoints.put("overall", "/health");
        healthEndpoints.put("ping", "/health/ping");
        healthEndpoints.put("database", "/health/database");
        response.put("health", healthEndpoints);

        // Documentação
        Map<String, Object> documentation = new HashMap<>();
        documentation.put("swagger", "/swagger-ui.html");
        response.put("documentation", documentation);
        
        // Informações adicionais
        Map<String, Object> info = new HashMap<>();
        info.put("description", "Sistema de E-commerce com Arquitetura Hexagonal");
        info.put("author", "Prof. Danilo Aparecido");
        info.put("course", "Arquiteturas de Software Modernas");
        info.put("platform", "Torne-se um Programador");
        response.put("info", info);
        
        return ResponseEntity.ok(response);
    }
}