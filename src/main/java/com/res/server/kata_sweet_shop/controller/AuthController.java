package com.res.server.kata_sweet_shop.controller;


import com.res.server.kata_sweet_shop.dto.AuthResponse;
import com.res.server.kata_sweet_shop.dto.LoginRequest;
import com.res.server.kata_sweet_shop.dto.RegisterRequest;
import com.res.server.kata_sweet_shop.entity.User;
import com.res.server.kata_sweet_shop.repository.UserRepository;
import com.res.server.kata_sweet_shop.security.JwtUtil;
import com.res.server.kata_sweet_shop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService, UserRepository userRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User saved = userService.register(request);
        return ResponseEntity.ok("User registered: " + saved.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // authentication successful
        User u = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(u.getUsername(), u.getRoles());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer") );
    }
}
