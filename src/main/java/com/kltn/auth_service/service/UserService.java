package com.kltn.auth_service.service;

import com.kltn.auth_service.dto.response.UserResponse;
import com.kltn.auth_service.entity.User;
import com.kltn.auth_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponse findById(String id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not existed."));

        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getPhoneNumber()
        );

        return userResponse;
    }
}
