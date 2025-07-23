package com.ecommerce.adapters.in.rest;

import com.ecommerce.adapters.in.rest.dto.CreateUserRequest;
import com.ecommerce.adapters.in.rest.dto.UserResponse;
import com.ecommerce.application.ports.in.CreateUserUseCase;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
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
} 