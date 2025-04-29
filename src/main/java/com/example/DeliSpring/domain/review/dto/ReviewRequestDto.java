package com.example.DeliSpring.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @NotBlank(message = "별점은 1점부터 5점까지 가능합니다")  // 리뷰 작성시 1~5점 가능하지만, 리뷰 조회에서는 3~5점만 보여지도록 할 예정임
    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank(message = "코멘트는 필수입니다.")
    private String comment;

    private Long orderId;
}
