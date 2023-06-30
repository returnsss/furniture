package com.teamproject.furniture.order.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {
    private String orderNum;        // 주문번호
    private String userId;          // 사용자 아이디
    private String orderName;       // 주문자 이름
    private String orderTel;        // 주문자 번호
    private String orderEmail;      // 주문자 이메일
    private String receiveName;     // 받는사람 이름
    private String receiveTel;      // 받는사람 번호
    private String receiveAddress;  // 받는사람 주소
    private OrderStep orderStep;       // 주문 단계
    private int payAmount;       // 결제 금액
    private LocalDateTime orderDate;// 주문한 시간
    private LocalDateTime payDate; // 결제한 시간
    private List<OrderDataDto> orderDataDtoList = new ArrayList<>();

    @QueryProjection
    public OrderInfoDto(String orderNum, String userId, String orderName, String orderTel, String orderEmail, String receiveName, String receiveTel, String receiveAddress, OrderStep orderStep, int payAmount, LocalDateTime orderDate, LocalDateTime payDate) {
        this.orderNum = orderNum;
        this.userId = userId;
        this.orderName = orderName;
        this.orderTel = orderTel;
        this.orderEmail = orderEmail;
        this.receiveName = receiveName;
        this.receiveTel = receiveTel;
        this.receiveAddress = receiveAddress;
        this.orderStep = orderStep;
        this.payAmount = payAmount;
        this.orderDate = orderDate;
        this.payDate = payDate;
    }


}
