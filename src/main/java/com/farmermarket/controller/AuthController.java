package com.farmermarket.controller;

import com.farmermarket.dto.request.LoginRequest;
import com.farmermarket.dto.response.LoginResponse;
import com.farmermarket.security.CustomUserDetailsService;
import com.farmermarket.security.JwtService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    // ══════════════════════════════
    // API — LOGIN
    // POST /api/auth/login
    // ══════════════════════════════
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {

        // STEP 1: Verify email + password.
        // Spring Security calls CustomUserDetailsService
        // internally and checks the BCrypt hash.
        // Throws AuthenticationException if invalid.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // STEP 2: Credentials are valid — load the user
        // to build the JWT claims.
        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(request.getEmail());

        // STEP 3: Generate the signed JWT.
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                new LoginResponse(token, "Bearer")
        );
    }
}