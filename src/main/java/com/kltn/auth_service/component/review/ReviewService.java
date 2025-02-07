package com.kltn.auth_service.component.review;

import com.kltn.auth_service.component.product.Product;
import com.kltn.auth_service.component.product.ProductRepository;
import com.kltn.auth_service.component.review.dto.request.ReviewRequest;
import com.kltn.auth_service.component.review.dto.response.ReviewResponse;
import com.kltn.auth_service.component.review.mapper.ReviewMapper;
import com.kltn.auth_service.dto.UserDTO;
import com.kltn.auth_service.dto.response.UserResponse;
import com.kltn.auth_service.entity.User;
import com.kltn.auth_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ProductRepository productRepository;
    private final ReviewMapper mapper;
    private final UserRepository userRepository;

    public UserResponse findUserById(String id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not existed."));

        return UserResponse.builder()
                .id(existingUser.getId())
                .firstname(existingUser.getFirstname())
                .lastname(existingUser.getLastname())
                .email(existingUser.getEmail())
                .phoneNumber(existingUser.getPhoneNumber())
                .build();
    }

    public ReviewResponse create(ReviewRequest request) {
        UserResponse user = findUserById(request.getUserId());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not existed"));

        Review review = mapper.toReview(request);
        repository.save(review);

        ReviewResponse reviewResponse = mapper.toReviewResponse(review);
        reviewResponse.setUser(user);

        return reviewResponse;
    }

    public void delete(String id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not existing."));

        repository.delete(review);
    }
}
