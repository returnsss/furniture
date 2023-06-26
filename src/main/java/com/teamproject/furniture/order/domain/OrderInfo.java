package com.teamproject.furniture.order.domain;

import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.dtos.OrderStep;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate // 수정된 부분만 쿼리문 만들도록
@NoArgsConstructor // 기본 생성자
@EntityListeners(value = {AuditingEntityListener.class})
public class OrderInfo {
    @Id
    private String orderNum;        // key
    private String userId;          // 사용자 아이디
    private String orderName;       // 주문자 이름
    private String orderTel;        // 주문자 번호
    private String orderEmail;      // 주문자 이메일
    private String receiveName;     // 받는사람 이름
    private String receiveTel;      // 받는사람 번호
    private String receiveAddress;  // 받는사람 주소
    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_step")
    private OrderStep orderStep;       // 주문 단계
    private int payAmount;          // 결제 금액

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate;// 주문한 시간

    @LastModifiedDate
    private LocalDateTime payDate; // 결제한 시간


    public OrderInfo(OrderInfoDto orderInfoDto){
        this.orderNum = orderInfoDto.getOrderNum();
        this.userId = orderInfoDto.getUserId();
        this.orderName = orderInfoDto.getOrderName();
        this.orderTel = orderInfoDto.getOrderTel();
        this.orderEmail = orderInfoDto.getOrderEmail();
        this.receiveName = orderInfoDto.getReceiveName();
        this.receiveTel = orderInfoDto.getReceiveTel();
        this.receiveAddress = orderInfoDto.getReceiveAddress();
        this.orderStep = orderInfoDto.getOrderStep();
        this.payAmount = orderInfoDto.getPayAmount();
    }

}
