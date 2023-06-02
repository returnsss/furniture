package com.teamproject.furniture.order.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderInfoDto {
    private Long orderNo;        // 주문번호
    private String userId;          // 사용자 아이디
    private String orderName;       // 주문자 이름
    private String orderTel;        // 주문자 번호
    private String orderEmail;      // 주문자 이메일
    private String receiveName;     // 받는사람 이름
    private String receiveTel;      // 받는사람 번호
    private String receiveAddress;  // 받는사람 주소
    private String orderStep;       // 주문 단계
    private int payAmount;       // 결제 금액
    private LocalDateTime orderDate;// 주문한 시간
    private LocalDateTime payDate; // 결제한 시간
}
