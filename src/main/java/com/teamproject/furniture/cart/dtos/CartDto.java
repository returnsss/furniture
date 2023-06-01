package com.teamproject.furniture.cart.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private Long cartId;

    private Long productId;

    private String productName;

    private String userId;

    private String orderNum;

    private int productPrice;

    private int cnt;

    //@QueryProjection
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
