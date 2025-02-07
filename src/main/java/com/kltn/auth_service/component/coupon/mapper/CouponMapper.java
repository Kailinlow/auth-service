package com.kltn.auth_service.component.coupon.mapper;

import com.kltn.auth_service.component.coupon.Coupon;
import com.kltn.auth_service.component.coupon.dto.request.CouponRequest;
import com.kltn.auth_service.component.coupon.dto.response.CouponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    Coupon toCoupon(CouponRequest request);

    CouponResponse toCouponResponse(Coupon coupon);
    List<CouponResponse> toCouponResponseList(List<Coupon> couponList);

    void updateCouponFromRequest(CouponRequest request, @MappingTarget Coupon coupon);
}
