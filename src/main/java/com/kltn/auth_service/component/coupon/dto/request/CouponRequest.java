package com.kltn.auth_service.component.coupon.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {
    private String code;

    private BigDecimal discount;

    private BigDecimal weight;

    private String typeCoupon;

    private String expirationDate;
}
