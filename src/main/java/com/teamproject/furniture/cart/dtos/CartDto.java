package com.teamproject.furniture.cart.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    private Long cartId;

    private Long productId;

    private String productName;

    private String userId;

    private String orderNum;

    private int productPrice;

    private int cnt;

    private String imgPath;



    public CartDto(Long cartId, Long productId, String productName, String userId, String orderNum, int productPrice, int cnt) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.orderNum = orderNum;
        this.productPrice = productPrice;
        this.cnt = cnt;

    }
}
