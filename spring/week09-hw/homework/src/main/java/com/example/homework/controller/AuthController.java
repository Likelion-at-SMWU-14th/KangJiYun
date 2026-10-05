package com.example.homework.controller;

import com.example.homework.dto.AccessTokenResponse;
import com.example.homework.dto.LoginRequest;
import com.example.homework.dto.SignupRequest;
import com.example.homework.dto.TokenResponse;
import com.example.homework.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String REFRESH_COOKIE = "refreshToken";

    private final AuthService authService;
    private final long refreshExpiration;

    public AuthController(
            AuthService authService,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.authService = authService;
        this.refreshExpiration = refreshExpiration;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody SignupRequest request
    ) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        return toResponse(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<AccessTokenResponse> reissue(
            @CookieValue(name = REFRESH_COOKIE, required = false) String refreshToken
    ) {
        return toResponse(authService.reissue(refreshToken));
    }

    private ResponseEntity<AccessTokenResponse> toResponse(TokenResponse tokens) {

        ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE, tokens.refreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/api/auth/reissue")
                .maxAge(Duration.ofMillis(refreshExpiration))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AccessTokenResponse(tokens.accessToken()));
    }
}