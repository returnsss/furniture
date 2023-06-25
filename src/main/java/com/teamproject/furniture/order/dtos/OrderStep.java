package com.teamproject.furniture.order.dtos;

import lombok.Getter;

@Getter
public enum OrderStep {
    OrderReceive("주문접수"),
    PayReceive("입금확인"),
    ShippingPrepare("배송준비중"),
    ShippingDelay("배송대기"),
    ShippingProgress("배송중"),
    ShippingComplete("배송완료"),
    OrderCancel("주문취소"),
    ExchangeRequest("교환요청"),
    ExchangeComplete("교환완료"),
    RefundRequest("환불요청"),
    RefundComplete("환불완료"),
    OrderFail("주문실패");

    private final String step;

    OrderStep(String step) {
        this.step = step;
    }

}
