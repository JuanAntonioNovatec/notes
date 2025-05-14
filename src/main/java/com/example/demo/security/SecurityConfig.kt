package com.example.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
open class SecurityConfig {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Si quieres configurar el acceso, descomenta y ajusta la línea:
        // http.authorizeHttpRequests { authz -> authz.requestMatchers(HttpMethod.POST, "/api/users").permitAll() }
        //     .anyRequest().authenticated()

        http.authorizeHttpRequests { authz -> authz.anyRequest().permitAll() }
            .csrf { it.disable() }
        return http.build()
    }

    @Bean
    open fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}