package com.ecommerce.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
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
        
        // Endpoints de Teste
        Map<String, Object> testEndpoints = new HashMap<>();
        testEndpoints.put("users", "/api/test/users");
        testEndpoints.put("products", "/api/test/products");
        testEndpoints.put("orders", "/api/test/orders");
        testEndpoints.put("create-order", "/api/test/create-order");
        response.put("test", testEndpoints);
        
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