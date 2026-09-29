package com.example.seminar.dto;

public record LoginRequest(
        String email,
        String password
) {
}