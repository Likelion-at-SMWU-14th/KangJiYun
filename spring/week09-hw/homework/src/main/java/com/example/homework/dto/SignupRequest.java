package com.example.homework.dto;

public record SignupRequest(
        String email,
        String password,
        String name
) {
}