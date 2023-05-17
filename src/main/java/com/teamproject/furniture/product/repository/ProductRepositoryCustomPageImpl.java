package com.teamproject.furniture.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.furniture.product.dtos.ProductPageDto;
import com.teamproject.furniture.product.dtos.QProductPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.teamproject.furniture.product.model.QProduct.product;

import java.util.List;

@Repository
public class ProductRepositoryCustomPageImpl implements ProductRepositoryCustomPage{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProductRepositoryCustomPageImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ProductPageDto> selectProductList(String searchVal, Pageable pageable) {
        List<ProductPageDto> content = getProductDtos(searchVal, pageable);
        Long count = getCount(searchVal);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchVal) {
        Long count = queryFactory
                .select(product.count())
                .from(product)
                .fetchOne();
        return count;
    }

    private List<ProductPageDto> getProductDtos(String searchVal, Pageable pageable) {
        List<ProductPageDto> content = queryFactory
                .select(new QProductPageDto(
                        product.productName
                        ,product.description
                        ,product.fileName
                        ,product.registDay))
                .from(product)
                .orderBy(product.productName.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
        return content;
    }
}
