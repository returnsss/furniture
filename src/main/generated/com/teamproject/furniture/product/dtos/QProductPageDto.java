package com.teamproject.furniture.product.dtos;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.teamproject.furniture.product.dtos.QProductPageDto is a Querydsl Projection type for ProductPageDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductPageDto extends ConstructorExpression<ProductPageDto> {

    private static final long serialVersionUID = -2120113471L;

    public QProductPageDto(com.querydsl.core.types.Expression<String> productName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<String> registDay) {
        super(ProductPageDto.class, new Class<?>[]{String.class, String.class, String.class, String.class}, productName, description, fileName, registDay);
    }

}

