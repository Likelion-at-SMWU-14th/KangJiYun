package com.example.homework.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private static final String TYPE_CLAIM = "type";
    public static final String ACCESS = "access";
    public static final String REFRESH = "refresh";

    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String createAccessToken(String email) {
        return createToken(email, ACCESS, accessExpiration);
    }

    public String createRefreshToken(String email) {
        return createToken(email, REFRESH, refreshExpiration);
    }

    private String createToken(String email, String type, long expiration) {
        Date now = new Date();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(email)
                .claim(TYPE_CLAIM, type)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public String getType(String token) {
        return parseClaims(token).get(TYPE_CLAIM, String.class);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}