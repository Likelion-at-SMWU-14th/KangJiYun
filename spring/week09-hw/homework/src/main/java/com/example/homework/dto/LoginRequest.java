package com.example.homework.dto;

public record LoginRequest(
        String email,
        String password
) {
}