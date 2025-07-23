package com.ecommerce.application.services;

import com.ecommerce.application.ports.in.CreateUserUseCase;
import com.ecommerce.application.ports.out.UserRepository;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User createUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        
        User user = new User(name, email, password);
        return userRepository.save(user);
    }
} 