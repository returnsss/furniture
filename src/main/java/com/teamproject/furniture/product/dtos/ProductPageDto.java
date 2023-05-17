package com.teamproject.furniture.product.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPageDto {

//    private Long productId;     // 제품 고유 아이디
    private String productName; // 제품명
    //private int productPrice;   // 제품가격
    private String description; // 제품 설명
    //private String category;    // 카테고리
    //private int productsInStock;// 제품 남은 수량
    private String fileName;    // 이미지 파일 이름
    private String registDay;   // 등록 날짜


    @QueryProjection
    public ProductPageDto(String productName, String description, String fileName, String registDay) {
        this.productName = productName;
        this.description = description;
        this.fileName = fileName;
        this.registDay = registDay;
    }
}
