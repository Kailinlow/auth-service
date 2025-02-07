package com.kltn.auth_service.component.order.dto.request;

import lombok.*;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String userId;
    private String orderDate;
}
