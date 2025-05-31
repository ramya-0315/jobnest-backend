package com.jobportal.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final long EXPIRATION_TIME = 86400000; // 24 hours in ms

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String role) {
        // Normalize role
        role = role.trim().toUpperCase();
        String finalRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        // Optional debug prints
        System.out.println("Incoming role: " + role);
        System.out.println("Final role in token: " + finalRole);

        return Jwts.builder()
                .subject(email)
                .claim("role", finalRole)
                .issuedAt(new Date())
                .expiration(Date.from(LocalDateTime.now().plusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            token = token.trim();
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        token = token.trim();
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Claims extractAllClaims(String token) {
        token = token.trim();
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
