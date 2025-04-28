package com.example.deliveryoutsourcing.domain.order.service;

import com.example.deliveryoutsourcing.domain.menu.entity.Menu;
import com.example.deliveryoutsourcing.domain.menu.repository.MenuRepository;
import com.example.deliveryoutsourcing.domain.order.dto.OrderRequestDto;
import com.example.deliveryoutsourcing.domain.order.dto.OrderResponseDto;
import com.example.deliveryoutsourcing.domain.order.dto.OrderUpdateRequestDto;
import com.example.deliveryoutsourcing.domain.order.entity.Order;
import com.example.deliveryoutsourcing.domain.order.entity.OrderLog;
import com.example.deliveryoutsourcing.domain.order.repository.OrderLogRepository;
import com.example.deliveryoutsourcing.domain.order.repository.OrderRepository;
import com.example.deliveryoutsourcing.domain.store.entity.Store;
import com.example.deliveryoutsourcing.domain.user.entity.User;
import com.example.deliveryoutsourcing.global.enums.OrderStatus;
import com.example.deliveryoutsourcing.global.error.ApiException;
import com.example.deliveryoutsourcing.global.error.response.ErrorType;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderLogRepository orderLogRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void createOrder(Long userId, OrderRequestDto requestDto) {

        if (requestDto.getMenuId() == null) {  // 메뉴 선택 안할때 에러터트리기
            throw new ApiException(ErrorType.MENU_NOT_SELECTED);
        }

        Menu menu = menuRepository.findById(requestDto.getMenuId())  // DB에서 해당 메뉴 찾아보기
            .orElseThrow(() -> new ApiException(ErrorType.MENU_NOT_FOUND));

        Store store = menu.getStore();  // 가게 확인

        if (menu.getPrice() < store.getMinOrderPrice()) {  // 최소주문금액 확인
            throw new ApiException(ErrorType.ORDER_PRICE_LOW);
        }

        LocalTime now = LocalTime.now();  // 영업시간 확인
        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new ApiException(ErrorType.STORE_CLOSED);
        }

        Order order = Order.builder()  // 주문 생성
            .user(User.builder().id(userId).build())
            .store(store)
            .menu(menu)
            .price(menu.getPrice())
            .status(OrderStatus.REQUESTED)
            .build();

        orderRepository.save(order);  // 주문 저장

        // 주문 생성 후 바로 OrderLog 남기기
        OrderLog orderLog = OrderLog.builder()
            .order(order)
            .store(store)
            .statusBefore(null) // 주문 최초 생성이니까 이전상태 없음!
            .statusAfter(OrderStatus.REQUESTED)
            .build();

        orderLogRepository.save(orderLog);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long ownerId, Long orderId, OrderUpdateRequestDto requestDto) {

        Order order = orderRepository.findById(orderId) // 주문 조회
            .orElseThrow(() -> new ApiException(ErrorType.ORDER_NOT_FOUND));

        if (!order.getStore().getOwner().getId().equals(ownerId)) {  // 가게 사장님인지 확인
            throw new ApiException(ErrorType.ORDER_OWNER_FORBIDDEN);
        }

        OrderStatus currentStatus = order.getStatus(); // 현재 주문상태 확인
        OrderStatus nextStatus = requestDto.getStatusAfter(); // 변경하려는 주문상태 확인

        if (!currentStatus.canTransition(nextStatus)) {  // 주문 상태 변경 검증
            throw new ApiException(ErrorType.ORDER_INVALID_STATUS_CHANGE);
        }

        order.updateStatus(nextStatus);  // 검증 끝 >> 주문 상태 변경

        // OrderLog 기록
        OrderLog orderLog = OrderLog.builder()
            .order(order)
            .store(order.getStore())
            .statusBefore(currentStatus)
            .statusAfter(nextStatus)
            .build();

        orderLogRepository.save(orderLog); // Order은 (위에서) 그냥 업데이트, OrdeLog는 새로 저장됨
    }

    @Override
    @Transactional(readOnly = true)  // 조회만 가능
    public List<OrderResponseDto> getMyOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);  // userId로 본인이 주문한 모든 주문 조회

        return orders.stream()
            .map(order -> OrderResponseDto.builder()
                .orderMenu(order.getMenu().getName())
                .orderPrice(order.getPrice())
                .orderStatus(order.getStatus())
                .build())
            .toList();
    }


}

