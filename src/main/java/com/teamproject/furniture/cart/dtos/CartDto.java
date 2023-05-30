package com.teamproject.furniture.cart.dtos;

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
}
