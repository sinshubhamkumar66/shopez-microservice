package com.shopez.authService.service;

import com.shopez.authService.dto.AuthResponse;
import com.shopez.authService.dto.LoginRequest;
import com.shopez.authService.dto.SignupRequest;
import com.shopez.authService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private com.shopez.authService.repository.UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.shopez.authService.util.JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(String.valueOf(request.getRole()));
        // optionally set email/roles if present in request
        userRepository.save(user);
        return "User registered successfully";
    }


    public AuthResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

                return AuthResponse.builder()
                        .token(token)
                        .username(user.getUsername())
                        .build();
            } else {
                throw new RuntimeException("Authentication failed");
            }
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid username or password", ex);
        }
    }

}

