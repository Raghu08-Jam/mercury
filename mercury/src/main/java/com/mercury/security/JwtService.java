package com.mercury.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    Long expirationTime = 1000L * 60L * 60L; // 1 hour in milliseconds

    public String generateToken(Long customerId, String email, String role) {
        return Jwts
                .builder()
                .claim("customerId", customerId)
                .claim("role", role)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // Token valid for 1 hour
                .signWith(getSigningKey())
                .compact();

    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long extractCustomerId(String token) {
        return extractAllClaims(token).get("customerId", Long.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenValid(String token) {
        try {
            return extractAllClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }


}
