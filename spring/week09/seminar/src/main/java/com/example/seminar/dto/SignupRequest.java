package com.example.seminar.dto;

public record SignupRequest(
        String email,
        String password,
        String name
) {
}