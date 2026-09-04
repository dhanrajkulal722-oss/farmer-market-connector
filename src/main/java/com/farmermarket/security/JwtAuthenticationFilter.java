package com.farmermarket.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService
            customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtService = jwtService;
        this.customUserDetailsService =
                customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Get Authorization header
        String authHeader =
                request.getHeader("Authorization");

        // Check whether JWT token exists
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // Remove "Bearer " and get token
        String jwtToken =
                authHeader.substring(7);

        // Extract email from JWT
        String email =
                jwtService.extractUsername(jwtToken);

        // Authenticate only if not already authenticated
        if (email != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            // Load user from database
            UserDetails userDetails =
                    customUserDetailsService
                            .loadUserByUsername(email);

            // Validate JWT
            if (jwtService.isTokenValid(
                    jwtToken,
                    userDetails)) {

                // Create authentication object
                UsernamePasswordAuthenticationToken
                        authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Add request details
                authenticationToken
                        .setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request)
                        );

                // Store authentication in SecurityContext
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authenticationToken
                        );
            }
        }

        // Continue to next filter
        filterChain.doFilter(
                request,
                response
        );
    }
}