package com.kltn.auth_service.component.review.dto.response;

import com.kltn.auth_service.dto.UserDTO;
import com.kltn.auth_service.dto.response.UserResponse;
import lombok.*;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private String id;
    private Integer rating;
    private String comment;
    private String commentStatus;
    private UserResponse user;
}
