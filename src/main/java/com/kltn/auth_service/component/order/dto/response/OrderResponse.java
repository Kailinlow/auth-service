package com.kltn.auth_service.component.order.dto.response;

import com.kltn.auth_service.component.coupon.dto.response.CouponResponse;
import com.kltn.auth_service.component.orderItem.dto.response.OrderItemResponse;
import com.kltn.auth_service.dto.response.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;

    private BigDecimal totalAmount;

    private LocalDateTime orderDate;

    private String paymentMethod;

    private String shippingCode;

    private String status;

    private UserResponse user;

    private CouponResponse coupon;

    private List<OrderItemResponse> orderItemList;
}
