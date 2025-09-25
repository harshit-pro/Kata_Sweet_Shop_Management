package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.RegisterRequest;
import com.res.server.kata_sweet_shop.entity.User;
import com.res.server.kata_sweet_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional // why using Transactional here?
    // because we want to make sure that the user is saved in the database
    // and if there is any error, the transaction is rolled back
    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername((request.getUsername()))){
            throw new IllegalArgumentException("Username is already taken");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add("USER"); // default role
        return userRepository.save(user);
    }
}
