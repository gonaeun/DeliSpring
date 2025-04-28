package com.example.deliveryoutsourcing.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {
    private Long orderId;
    private int rating;
    private String comment;
}
