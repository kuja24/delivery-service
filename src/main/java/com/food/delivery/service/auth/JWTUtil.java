package com.food.delivery.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private long JWT_EXPIRATION = 1000 * 60 * 60; // 1 hour expiration

    private final SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public List<GrantedAuthority> getRolesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("roles"); // Ensure your claim is named "roles"
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    }

