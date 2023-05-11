package com.teamproject.furniture.product.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductDto {
    //private Long productId;     // 제품 고유 아이디
    private String productName; // 제품명
    private int productPrice;   // 제품가격
    private String description; // 제품 설명
    private String category;    // 카테고리
    private int productsInStock;// 제품 남은 수량
    private String fileName;    // 이미지 파일 이름
    //private String registDay;   // 등록 날짜
}
