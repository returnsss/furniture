package com.teamproject.furniture.cart.domain;

import com.teamproject.furniture.cart.dtos.AddCartDto;
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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Long productId;

    private String productName;

    private String userId;

    private String orderNum;

    private int productPrice;

    private int cnt;


    public Cart(AddCartDto addCartDto){
        this.productId = addCartDto.getProductId();
        this.productName = addCartDto.getProductName();
        this.userId = addCartDto.getUserId();
        this.orderNum = addCartDto.getOrderNum();
        this.productPrice = addCartDto.getProductPrice();
        this.cnt = addCartDto.getCnt();
    }


}
