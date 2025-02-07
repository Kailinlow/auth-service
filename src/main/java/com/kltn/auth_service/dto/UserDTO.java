package com.kltn.auth_service.dto;

import lombok.*;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
