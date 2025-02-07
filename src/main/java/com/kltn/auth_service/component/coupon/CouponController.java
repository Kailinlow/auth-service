package com.kltn.auth_service.component.coupon;

import com.kltn.auth_service.component.coupon.dto.request.CouponCodeRequest;
import com.kltn.auth_service.component.coupon.dto.request.CouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CouponRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/code")
    public ResponseEntity<?> findByCode(@RequestBody CouponCodeRequest request){
        return ResponseEntity.ok(service.findByCode(request.getCode()));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CouponRequest request) {
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Coupon has been deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/use/{id}")
    public ResponseEntity<?> useCoupon(@PathVariable String id) {
        return ResponseEntity.ok(service.useCoupon(id));
    }

    @PutMapping("/expired/{id}")
    public ResponseEntity<?> expiredCoupon(@PathVariable String id) {
        return ResponseEntity.ok(service.expiredCoupon(id));
    }
}
