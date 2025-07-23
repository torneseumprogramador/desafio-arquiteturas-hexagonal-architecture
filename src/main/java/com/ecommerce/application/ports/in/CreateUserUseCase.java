package com.ecommerce.application.ports.in;

import com.ecommerce.domain.model.User;

public interface CreateUserUseCase {
    User createUser(String name, String email, String password);
} 