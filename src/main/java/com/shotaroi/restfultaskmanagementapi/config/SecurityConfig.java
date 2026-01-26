package com.shotaroi.restfultaskmanagementapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // For learning APIs, disable CSRF (it blocks POST/PUT/DELETE without browser tokens)
                .csrf(csrf -> csrf.disable())

                // Allow these endpoints without logging in
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/tasks/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )

                // Needed so H2 console can render in the browser
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // If you don't need Basic login right now, you can keep it or remove it.
                .httpBasic(basic -> {}) // keep basic for other endpoints
                .build();
    }
}
