package com.kltn.auth_service.component.brand;

import com.kltn.auth_service.component.brand.dto.request.BrandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BrandRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody BrandRequest request, @PathVariable String id) {
        return ResponseEntity.ok((service.update(request, id)));
    }


}
