package com.example.DeliSpring.domain.review.repository;

import com.example.DeliSpring.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByOrderId(Long orderId);

    List<Review> findAllByStoreIdAndRatingBetweenOrderByCreatedAtDesc(Long storeId, int minRating, int maxRating);
    // 해당 가게의 3~5점대의 별점만 최신순으로 조회. (리뷰생성시 1~5점 가능하지만 리뷰조회시 3~5점만 보이도록)
}
