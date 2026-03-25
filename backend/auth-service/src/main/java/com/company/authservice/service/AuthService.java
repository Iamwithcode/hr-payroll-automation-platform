package com.company.authservice.service;

import com.company.authservice.dto.LoginRequest;
import com.company.authservice.dto.LoginResponse;
import com.company.authservice.entity.User;
import com.company.authservice.repository.UserRepository;
import com.company.authservice.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().getName());

        return new LoginResponse(token, user.getUsername(), user.getRole().getName());
    }
}