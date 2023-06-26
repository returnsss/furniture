package com.teamproject.furniture.product.repository;

import com.querydsl.core.BooleanBuilder;
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
        BooleanBuilder whereClause = new BooleanBuilder();

        if (searchVal != null && !searchVal.isEmpty()) {
            whereClause.and(product.productName.containsIgnoreCase(searchVal));
        }

        Long count = queryFactory
                .select(product.count())
                .from(product)
                .where(whereClause)
                .fetchOne();
        return count;
    }

    private List<ProductPageDto> getProductDtos(String searchVal, Pageable pageable) {

        BooleanBuilder whereClause = new BooleanBuilder();  // Create a BooleanBuilder for dynamic WHERE conditions

        // Add conditions based on the searchVal
        if (searchVal != null && !searchVal.isEmpty()) {
            // Assuming 'product.productName' is the field to be searched
            whereClause.and(product.productName.containsIgnoreCase(searchVal));
        }

        List<ProductPageDto> content = queryFactory
                .select(new QProductPageDto(
                        product.productId,
                        product.productName,
                        product.productPrice,
                        product.description,
                        product.category,
                        product.productsInStock,
                        product.imgPath))
                .from(product)
                .where(whereClause)  // Apply the WHERE conditions
                .orderBy(product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }
}
