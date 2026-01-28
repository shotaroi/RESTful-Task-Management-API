package com.shotaroi.restfultaskmanagementapi.controller;

import com.shotaroi.restfultaskmanagementapi.dto.AuthResponse;
import com.shotaroi.restfultaskmanagementapi.dto.LoginRequest;
import com.shotaroi.restfultaskmanagementapi.dto.RegisterRequest;
import com.shotaroi.restfultaskmanagementapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
