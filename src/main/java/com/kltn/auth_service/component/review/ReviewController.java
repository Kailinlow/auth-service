package com.kltn.auth_service.component.review;

import com.kltn.auth_service.component.review.dto.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @GetMapping("{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(service.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>("Review " + " has been deleted successfully.", HttpStatus.OK);
    }

}
