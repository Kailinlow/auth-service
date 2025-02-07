package com.kltn.auth_service.component.orderItem.dto.response;

import com.kltn.auth_service.component.product.dto.response.ProductResponse;
import com.kltn.auth_service.component.product.dto.response.ProductShortResponse;
import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private String id;

    private ProductShortResponse product;

    private Integer quantity;

    private BigDecimal price;
}
