package com.tennis.platform.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import javax.crypto.SecretKey;
import javax.annotation.PostConstruct;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils {

    private final String secret;
    private final long expirationMs;

    public JwtUtils(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    private SecretKey key;

    @PostConstruct
    public void init() {
        // 使用 Keys 生成安全的密鑰
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
} 