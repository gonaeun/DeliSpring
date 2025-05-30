package com.example.DeliSpring.domain.review.service;

import com.example.DeliSpring.domain.review.dto.ReviewRequestDto;
import com.example.DeliSpring.domain.review.dto.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    void createReview(Long userId, ReviewRequestDto requestDto);
    List<ReviewResponseDto> getReviews(Long storeId, int minRating, int maxRating);
}
