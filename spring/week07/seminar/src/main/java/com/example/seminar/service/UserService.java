package com.example.seminar.service;

import com.example.seminar.dto.UserResponse;
import com.example.seminar.dto.UserSaveRequest;
import com.example.seminar.dto.UserUpdateRequest;
import com.example.seminar.entity.User;
import com.example.seminar.global.exception.DuplicateEmailException;
import com.example.seminar.global.exception.UserNotFoundException;
import com.example.seminar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserSaveRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }
        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .age(request.getAge())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (request.getEmail() != null
                && userRepository.existsByEmailAndIdNot(request.getEmail(), userId)) {
            throw new DuplicateEmailException();
        }
        user.update(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getAge()
        );
        return UserResponse.from(user);
    }
}
