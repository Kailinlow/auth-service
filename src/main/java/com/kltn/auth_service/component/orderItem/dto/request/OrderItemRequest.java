package com.kltn.auth_service.component.orderItem.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {
    private Integer quantity;

    private String orderId;

    private String productId;
}
