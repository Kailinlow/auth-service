package com.kltn.auth_service.component.productAttributes;

import com.kltn.auth_service.component.productAttributes.dto.request.CreateProductAttributesRequest;
import com.kltn.auth_service.component.productAttributes.dto.request.UpdateProductAttributesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-attribute")
@RequiredArgsConstructor
public class ProductAttributesController {
    private final ProductAttributesService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateProductAttributesRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/full")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody UpdateProductAttributesRequest request, @PathVariable String id) {
        return ResponseEntity.ok(service.update(request, id));
    }
}
