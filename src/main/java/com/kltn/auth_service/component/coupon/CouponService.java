package com.kltn.auth_service.component.coupon;

import com.kltn.auth_service.component.coupon.dto.request.CouponRequest;
import com.kltn.auth_service.component.coupon.dto.response.CouponResponse;
import com.kltn.auth_service.component.coupon.mapper.CouponMapper;
import com.kltn.auth_service.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository repository;
    private final CouponMapper mapper;


    public CouponResponse create(CouponRequest request) {

        Coupon coupon = mapper.toCoupon(request);

        Coupon savedCoupon = repository.save(coupon);

        return mapper.toCouponResponse(savedCoupon);
    }

    public CouponResponse findById(String id) {
        Coupon exisitingCoupon = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon is not existing."));

        return mapper.toCouponResponse(exisitingCoupon);
    }

    public PageResponse<CouponResponse> findAll(int page, int size) {
        Sort sort = Sort.by("discount").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = repository.findAll(pageable);

        return PageResponse.<CouponResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(mapper::toCouponResponse).toList())
                .build();
    }

    public CouponResponse findByCode(String code) {
        Coupon existingCoupon = repository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Coupon is not existing."));

        return mapper.toCouponResponse(existingCoupon);
    }

    public CouponResponse useCoupon(String id) {
        Coupon exisitingCoupon = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon is not existing."));

        exisitingCoupon.setStatus(CouponStatus.USED);
        repository.save(exisitingCoupon);

        return mapper.toCouponResponse(exisitingCoupon);
    }

    public CouponResponse expiredCoupon(String id) {
        Coupon exisitingCoupon = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon is not existing."));

        exisitingCoupon.setStatus(CouponStatus.EXPIRED);
        repository.save(exisitingCoupon);

        return mapper.toCouponResponse(exisitingCoupon);
    }

    public CouponResponse update(CouponRequest request, String id) {
        Coupon exisitingCoupon = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon is not existing."));

        mapper.updateCouponFromRequest(request, exisitingCoupon);

        Coupon updatedCoupon = repository.save(exisitingCoupon);

        return mapper.toCouponResponse(updatedCoupon);
    }

    public void delete(String id) {
        Coupon exisitingCoupon = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon is not existing."));

        repository.delete(exisitingCoupon);
    }
}
