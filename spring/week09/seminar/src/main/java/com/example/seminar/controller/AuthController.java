package com.example.seminar.controller;

import com.example.seminar.dto.LoginRequest;
import com.example.seminar.dto.SignupRequest;
import com.example.seminar.dto.TokenResponse;
import com.example.seminar.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody SignupRequest request
    ) {

        authService.signup(request);

        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}