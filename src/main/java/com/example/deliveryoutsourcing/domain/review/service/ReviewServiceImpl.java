package com.example.deliveryoutsourcing.domain.review.service;

import com.example.deliveryoutsourcing.domain.order.entity.Order;
import com.example.deliveryoutsourcing.domain.order.repository.OrderRepository;
import com.example.deliveryoutsourcing.domain.review.dto.ReviewRequestDto;
import com.example.deliveryoutsourcing.domain.review.dto.ReviewResponseDto;
import com.example.deliveryoutsourcing.domain.review.entity.Review;
import com.example.deliveryoutsourcing.domain.review.repository.ReviewRepository;
import com.example.deliveryoutsourcing.domain.store.entity.Store;
import com.example.deliveryoutsourcing.domain.store.repository.StoreRepository;
import com.example.deliveryoutsourcing.global.error.ApiException;
import com.example.deliveryoutsourcing.global.error.response.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.deliveryoutsourcing.global.enums.OrderStatus.COMPLETED;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public void createReview(Long userId, ReviewRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())  // 해당 주문건 있는지 확인
            .orElseThrow(() -> new ApiException(ErrorType.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(userId)) {  // 주문자와 리뷰남기려는 사용자 일치하는지 확인
            throw new ApiException(ErrorType.FORBIDDEN_ACCESS);
        }

        if (order.getStatus() != COMPLETED) {  // 주문완료상태에만 리뷰작성 가능
            throw new ApiException(ErrorType.ORDER_NOT_COMPLETED);
        }

        if (reviewRepository.existsByOrderId(order.getId())) {  // db에서 해당 주문건 리뷰 이미 존재하는지 확인
            throw new ApiException(ErrorType.REVIEW_ALREADY_EXISTS);
        }

        Review review = new Review(order.getStore(), order, requestDto.getRating(), requestDto.getComment());  // 리뷰생성
        reviewRepository.save(review);  // db에 리뷰 저장
    }

    @Override
    @Transactional(readOnly = true)  // 조회 전용
    public List<ReviewResponseDto> getReviews(Long storeId, int minRating, int maxRating) {
        Store store = storeRepository.findById(storeId)  // 가게 찾기
            .orElseThrow(() -> new ApiException(ErrorType.STORE_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllByStoreIdAndRatingBetweenOrderByCreatedAtDesc(
            storeId, minRating, maxRating
        );  // 해당 가게의 3~5점대의 별점만 최신순으로 조회해서 리스트 생성 (리뷰생성시 1~5점 가능하지만 리뷰조회시 3~5점만 보이도록)

        return reviews.stream()
            .map(review -> ReviewResponseDto.builder()
                .orderId(review.getOrder().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build())
            .toList();
    }
}
