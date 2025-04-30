package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz  // Usar authorizeHttpRequests en lugar de authorizeRequests
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Permitir acceso sin autenticación a este endpoint
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                );


        http.csrf().disable();
        return http.build();
    }
}