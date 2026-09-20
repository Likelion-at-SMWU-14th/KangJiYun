package com.example.seminar.controller;

import com.example.seminar.dto.UserResponse;
import com.example.seminar.dto.UserSaveRequest;
import com.example.seminar.dto.UserUpdateRequest;
import com.example.seminar.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void saveUser(@Valid @RequestBody UserSaveRequest request) {
        userService.saveUser(request);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponse updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }
}
