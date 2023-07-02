package com.teamproject.furniture.product.model;

import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate // 수정된 부분만 쿼리문 만들도록
@NoArgsConstructor // 기본 생성자
@EntityListeners(value = {AuditingEntityListener.class})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;     // 제품 고유 아이디
    private String productName; // 제품명
    private int productPrice;   // 제품가격
    private String description; // 제품 설명
    private String category;    // 카테고리
    private int productsInStock;// 제품 남은 수량
    private int lockCnt;        // 구매하려는 상품 갯수만큼 productsInStock에 제한
    private String fileName;    // 이미지 파일 이름

    private String imgPath;     // 이미지 조회 경로
    @CreatedDate
    private LocalDateTime registDay;   // 등록 날짜

    public Product(Long productId, String productName, int productPrice, String description, String category, int productsInStock, String fileName, LocalDateTime registDay) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.category = category;
        this.productsInStock = productsInStock;
        this.fileName = fileName;
        this.registDay = registDay;
    }

    public Product(AddProductDto addProductDto){
        this.productName = addProductDto.getProductName();
        this.productPrice = addProductDto.getProductPrice();
        this.description = addProductDto.getDescription();
        this.category = addProductDto.getCategory();
        this.productsInStock = addProductDto.getProductsInStock();
        this.fileName = addProductDto.getProductImage().getOriginalFilename();
    }


    public void updateProduct(UpdateProductDto updateProductDto) {

        this.productName = updateProductDto.getProductName();
        this.productPrice = updateProductDto.getProductPrice();
        this.description = updateProductDto.getDescription();
        this.category = updateProductDto.getCategory();
        this.productsInStock = updateProductDto.getProductsInStock();
        this.fileName = updateProductDto.getProductImage().getOriginalFilename();
    }




}
