package com.kltn.auth_service.component.coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, String> {
    Page<Coupon> findAll(Pageable pageable);
    Optional<Coupon> findByCode(String code);
}
