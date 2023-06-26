package com.teamproject.furniture.order.dtos;

import lombok.Getter;

@Getter
public enum OrderStep {
    ORDER_RECEIVE("주문접수"),
    PAY_RECEIVE("입금확인"),
    SHIPPING_PREPARE("배송준비중"),
    SHIPPING_DELAY("배송대기"),
    SHIPPING_PROGRESS("배송중"),
    SHIPPING_COMPLETE("배송완료"),
    ORDER_CANCEL("주문취소"),
    EXCHANGE_REQUEST("교환요청"),
    EXCHANGE_COMPLETE("교환완료"),
    REFUND_REQUEST("환불요청"),
    REFUND_COMPLETE("환불완료"),
    ORDER_FAIL("주문실패");

    private final String step;

    OrderStep(String step) {
        this.step = step;
    }

}
