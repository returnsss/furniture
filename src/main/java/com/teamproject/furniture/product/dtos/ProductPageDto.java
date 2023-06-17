package com.teamproject.furniture.product.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPageDto {

    private Long productId;

    private String productName; // 제품명

    private int productPrice;
    private String description; // 제품 설명
    private String category;    // 카테고리
    private int productsInStock;// 제품 남은 수량

    private String imgPath;    // 이미지 파일 이름



    @QueryProjection
    public ProductPageDto(Long productId, String productName, int productPrice, String description, String category, int productsInStock, String imgPath) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.category = category;
        this.productsInStock = productsInStock;
        this.imgPath = imgPath;
    }
}
