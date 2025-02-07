package com.kltn.auth_service.component.order.dto.request;

import com.kltn.auth_service.component.orderItem.dto.request.CreateOrderItemRequest;
import lombok.*;

import java.util.List;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    private String userId;
    private String couponId;
    private String orderDate;
    private String paymentMethod;
    private String shippingCode;
    private List<CreateOrderItemRequest> orderItemList;
}
