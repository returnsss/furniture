package com.teamproject.furniture.order.domain;

import com.teamproject.furniture.order.dtos.OrderDataDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@DynamicUpdate // 수정된 부분만 쿼리문 만들도록
@NoArgsConstructor // 기본 생성자
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;           // key
    private String orderNum;    // 주문번호
    private Long cartId;        // 카트key
    private String productId;   // 상품key
    private String productName; // 상품명
    private int productPrice;   // 상품 가격
    private int cnt;            // 주문 수량
    private int totalPrice;     // 총액


    public OrderData(OrderDataDto orderDataDto){
        this.num = orderDataDto.getNum();
        this.orderNum = orderDataDto.getOrderNum();
        this.cartId = orderDataDto.getCartId();
        this.productId = orderDataDto.getProductId();
        this.productName = orderDataDto.getProductName();
        this.productPrice = orderDataDto.getProductPrice();
        this.cnt = orderDataDto.getCnt();
        this.totalPrice = orderDataDto.getTotalPrice();
    }
}
