package com.ecommerce.adapters.in.rest;

import com.ecommerce.adapters.in.rest.dto.CreateUserRequest;
import com.ecommerce.adapters.in.rest.dto.UserResponse;
import com.ecommerce.application.ports.in.CreateUserUseCase;
import com.ecommerce.application.ports.out.UserRepository;
import com.ecommerce.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Criar Usuário", description = "Cria um novo usuário no sistema")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = createUserUseCase.createUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar Usuários", description = "Retorna todos os usuários cadastrados no sistema")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }
} 