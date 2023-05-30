package com.teamproject.furniture.order.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDataDto {
    private Long num;           // key
    private String orderNum;    // 주문번호
    private Long cartId;        // 카트key
    private String productId;   // 상품key
    private String productName; // 상품명
    private int productPrice;   // 상품 가격
    private int cnt;            // 주문 수량
    private int totalPrice;     // 총액
}
