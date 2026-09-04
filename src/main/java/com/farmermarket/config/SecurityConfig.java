package com.farmermarket.config;

import com.farmermarket.security.CustomUserDetailsService;
import com.farmermarket.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    // ==============================
    // PASSWORD ENCODER
    // ==============================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // ==============================
    // SECURITY FILTER CHAIN
    // ==============================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // REST API → CSRF is not required
            .csrf(csrf -> csrf.disable())

            // JWT → no HTTP session
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // API authorization rules
            .authorizeHttpRequests(auth -> auth

                // Public APIs
                .requestMatchers(
                    "/api/auth/**",
                    "/api/users/register"
                )
                .permitAll()

                // Everything else requires authentication
                .anyRequest()
                .authenticated()
            )

            // Add JWT filter before Spring Security's
            // UsernamePasswordAuthenticationFilter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // ==============================
    // AUTHENTICATION PROVIDER
    // ==============================

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        // Load user details from database
        provider.setUserDetailsService(
                customUserDetailsService
        );

        // Validate password using BCrypt
        provider.setPasswordEncoder(
                passwordEncoder()
        );

        return provider;
    }

    // ==============================
    // AUTHENTICATION MANAGER
    // ==============================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration
                .getAuthenticationManager();
    }
}