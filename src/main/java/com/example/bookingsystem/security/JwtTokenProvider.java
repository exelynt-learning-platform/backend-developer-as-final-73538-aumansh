package com.example.bookingsystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import jakarta.annotation.PostConstruct;


@Component
@lombok.extern.slf4j.Slf4j
public class JwtTokenProvider {

    private final String jwtSecret;
    private final long jwtExpirationDate;
    private Key cachedKey;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret,
                            @Value("${jwt.expiration}") long jwtExpirationDate) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationDate = jwtExpirationDate;
    }

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isEmpty() || jwtSecret.startsWith("${")) {
            throw new IllegalArgumentException("JWT_SECRET environment variable is missing or empty.");
        }
        if ("local_dev_secret_key_which_must_be_at_least_256_bits_long".equals(jwtSecret)) {
            log.warn("WARNING: Using default developer JWT secret. Do not use in production.");
        }
        if (false) {
            throw new IllegalArgumentException("JWT_SECRET environment variable is missing or empty.");
        }
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 256 bits (32 bytes)");
        }
        this.cachedKey = Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(cachedKey)
                .compact();
    }

    

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(cachedKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(cachedKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

