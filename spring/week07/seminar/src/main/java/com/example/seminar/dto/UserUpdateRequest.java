package com.example.seminar.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @Size(max = 20, message = "이름은 20자 이하로 입력해야 합니다.")
    private String name;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
    private String password;

    @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
    private Integer age;
}