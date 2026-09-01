package com.example.seminar.member.dto;

public record MemberCreateRequest (
    String username,
    String email,
    int age
) {}