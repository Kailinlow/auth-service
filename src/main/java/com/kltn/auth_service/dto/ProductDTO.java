package com.kltn.auth_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String sku;
    private BigDecimal price;
    private BigDecimal marketPrice;
    private Long stockQuantity;
}
