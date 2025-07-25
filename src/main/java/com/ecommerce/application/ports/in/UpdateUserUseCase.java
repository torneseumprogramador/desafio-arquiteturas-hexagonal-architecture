package com.ecommerce.application.ports.in;

import com.ecommerce.domain.model.User;

public interface UpdateUserUseCase {
    User updateUser(Long id, String name, String email, String password);
} 