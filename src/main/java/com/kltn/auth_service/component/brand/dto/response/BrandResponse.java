package com.kltn.auth_service.component.brand.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BrandResponse(
        String id,
        String name,
        String information,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
