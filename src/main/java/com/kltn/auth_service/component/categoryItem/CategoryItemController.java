package com.kltn.auth_service.component.categoryItem;

import com.kltn.auth_service.component.categoryItem.dto.request.CategoryItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category/items")
@RequiredArgsConstructor
public class CategoryItemController {
    private final CategoryItemService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryItemRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByCategoryId(@PathVariable String id) {
        return ResponseEntity.ok(service.findByCategoryId(id));
    }
}
