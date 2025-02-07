package com.kltn.auth_service.component.product;

import com.kltn.auth_service.component.image.ImageService;
import com.kltn.auth_service.component.product.dto.request.CreateProductRequest;
import com.kltn.auth_service.component.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ImageService imageService;

    @PostMapping
    public  ResponseEntity<?> create(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/short/{id}")
    public ResponseEntity<?> getShortInformation(@PathVariable String id) {
        return ResponseEntity.ok(productService.getShortInformation(id));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> findImages(@PathVariable String id) {
        return ResponseEntity.ok(imageService.findByProductId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
