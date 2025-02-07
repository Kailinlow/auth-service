package com.kltn.auth_service.auth.dto;

import com.kltn.auth_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String password;
    private Role role;
}
