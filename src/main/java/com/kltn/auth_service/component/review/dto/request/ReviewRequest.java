package com.kltn.auth_service.component.review.dto.request;

import lombok.*;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private Integer rating;

    private String comment;

    private String commentStatus;

    private String userId;

    private String productId;
}
