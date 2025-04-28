package com.example.deliveryoutsourcing.domain.review.controller;

import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.domain.review.dto.ReviewRequestDto;
import com.example.deliveryoutsourcing.domain.review.dto.ReviewResponseDto;
import com.example.deliveryoutsourcing.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> createReview(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody ReviewRequestDto requestDto
    ) {
        reviewService.createReview(userDetails.getUserId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviews(
        @RequestParam Long storeId,
        @RequestParam(defaultValue = "3") int minRating,
        @RequestParam(defaultValue = "5") int maxRating
    ) {
        return ResponseEntity.ok(reviewService.getReviews(storeId, minRating, maxRating));
    }
}
