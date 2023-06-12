package com.teamproject.furniture.product.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPageDto {

    private Long productId;

    private String productName; // 제품명

    private String description; // 제품 설명

    private String imgPath;    // 이미지 파일 이름



    @QueryProjection
    public ProductPageDto(Long productId, String productName, String description, String imgPath) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.imgPath = imgPath;
    }
}
