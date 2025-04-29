package com.example.DeliSpring.global.enums;

public enum OrderStatus {
    REQUESTED, ACCEPTED, COOKING, DELIVERING, COMPLETED;

    public boolean canTransition(OrderStatus nextStatus) {  // transition 허용 여부
        return switch (this) {
            case REQUESTED -> nextStatus == ACCEPTED;
            case ACCEPTED -> nextStatus == COOKING;
            case COOKING -> nextStatus == DELIVERING;
            case DELIVERING -> nextStatus == COMPLETED;  // 정해진 순서대로만 이동할 수 있도록 제어
            default -> false;   // 정의된 순서가 아니라면 허용안함
        };
    }
}