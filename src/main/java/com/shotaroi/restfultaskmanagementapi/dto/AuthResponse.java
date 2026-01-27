package com.shotaroi.restfultaskmanagementapi.dto;

import org.springframework.security.authorization.MapRequiredAuthoritiesRepository;

public class AuthResponse {
    private String token;

    public AuthResponse() {}
    public AuthResponse(String token) { this.token = token; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

}
