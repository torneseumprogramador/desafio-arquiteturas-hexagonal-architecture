package com.ecommerce.application.services;

import com.ecommerce.application.ports.in.CreateUserUseCase;
import com.ecommerce.application.ports.in.UpdateUserUseCase;
import com.ecommerce.application.ports.in.DeleteUserUseCase;
import com.ecommerce.application.ports.out.UserRepository;
import com.ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User createUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }
        
        User user = new User(name, email, password);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, String name, String email, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        // Verifica se o email já está em uso por outro usuário
        if (!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }
        
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        
        userRepository.deleteById(id);
    }
} 