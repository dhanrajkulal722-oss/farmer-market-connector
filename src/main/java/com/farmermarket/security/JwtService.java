package com.farmermarket.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // ==================================
    // JWT SECRET KEY
    // Reads from application.properties
    //
    // jwt.secret=...
    // ==================================

    @Value("${jwt.secret}")
    private String secretKey;


    // ==================================
    // JWT EXPIRATION
    // Reads from application.properties
    //
    // jwt.expiration=86400000
    // 86400000 milliseconds = 24 hours
    // ==================================

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    // ==================================
    // GENERATE TOKEN
    // ==================================

    public String generateToken(
            UserDetails userDetails) {

        Map<String, Object> extraClaims =
                new HashMap<>();

        return createToken(
                extraClaims,
                userDetails
        );
    }


    // ==================================
    // CREATE TOKEN
    // ==================================

    private String createToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {

        return Jwts.builder()

                // Extra information
                .setClaims(extraClaims)

                // Logged-in user's email
                .setSubject(
                        userDetails.getUsername()
                )

                // Token creation time
                .setIssuedAt(
                        new Date(
                                System.currentTimeMillis()
                        )
                )

                // Token expiry time
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + jwtExpiration
                        )
                )

                // Sign token using secret key
                .signWith(
                        getSignInKey(),
                        SignatureAlgorithm.HS256
                )

                // Convert to JWT String
                .compact();
    }


    // ==================================
    // EXTRACT USERNAME
    // ==================================

    public String extractUsername(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }


    // ==================================
    // EXTRACT SPECIFIC CLAIM
    // ==================================

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


    // ==================================
    // EXTRACT ALL CLAIMS
    // ==================================

    private Claims extractAllClaims(
            String token) {

        return Jwts.parserBuilder()

                .setSigningKey(
                        getSignInKey()
                )

                .build()

                .parseClaimsJws(token)

                .getBody();
    }


    // ==================================
    // VALIDATE TOKEN
    // ==================================

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        final String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        )
        && !isTokenExpired(token);
    }


    // ==================================
    // CHECK TOKEN EXPIRATION
    // ==================================

    private boolean isTokenExpired(
            String token) {

        return extractExpiration(token)
                .before(new Date());
    }


    // ==================================
    // EXTRACT EXPIRATION DATE
    // ==================================

    private Date extractExpiration(
            String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }


    // ==================================
    // CREATE SIGNING KEY
    // ==================================

    private SecretKey getSignInKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        secretKey
                );

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }
}