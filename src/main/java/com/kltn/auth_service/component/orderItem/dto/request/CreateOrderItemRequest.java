package com.kltn.auth_service.component.orderItem.dto.request;

import lombok.*;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderItemRequest {
    private Integer quantity;

    private String productId;
}
