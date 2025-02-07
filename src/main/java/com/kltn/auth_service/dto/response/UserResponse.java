package com.kltn.auth_service.dto.response;

import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
