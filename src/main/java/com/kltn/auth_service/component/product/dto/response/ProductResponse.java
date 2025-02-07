package com.kltn.auth_service.component.product.dto.response;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductResponse(
        String id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String name,
        String sku,
        String imageUrl,
        String description,
        BigDecimal cost,
        BigDecimal price,
        BigDecimal marketPrice,
        Long stockQuantity,
        BrandResponse brand
) {
    public record BrandResponse(
        String id,
        String name,
        String information
    ){}

}
