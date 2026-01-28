package com.shotaroi.restfultaskmanagementapi.service;

import com.shotaroi.restfultaskmanagementapi.dto.AuthResponse;
import com.shotaroi.restfultaskmanagementapi.dto.LoginRequest;
import com.shotaroi.restfultaskmanagementapi.dto.RegisterRequest;
import com.shotaroi.restfultaskmanagementapi.entity.AppUser;
import com.shotaroi.restfultaskmanagementapi.security.JwtService;
import com.shotaroi.restfultaskmanagementapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        String hash = passwordEncoder.encode(req.getPassword());
        userRepository.save(new AppUser(req.getUsername(), hash));
    }

    public AuthResponse login(LoginRequest req) {
        var user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
