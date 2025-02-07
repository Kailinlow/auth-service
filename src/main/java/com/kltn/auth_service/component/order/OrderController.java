package com.kltn.auth_service.component.order;

import com.kltn.auth_service.component.order.dto.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(service.createNewOrder(request));
    }

    @PutMapping("/proved")
    public ResponseEntity<?> provedOrder(@PathVariable String id) {
        return ResponseEntity.ok(service.updateStatusOrder(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findByUserId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") String id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
