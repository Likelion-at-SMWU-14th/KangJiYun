package com.example.homework.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}