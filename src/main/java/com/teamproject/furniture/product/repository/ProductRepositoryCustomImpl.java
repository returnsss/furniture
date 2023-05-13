package com.teamproject.furniture.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.furniture.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.teamproject.furniture.product.model.QProduct.product;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Product> searchProducts(String column, String keyword) {
        BooleanExpression searchCondition;

        if ("productName".equals(column)) {
            searchCondition = product.productName.containsIgnoreCase(keyword);
        } else if ("category".equals(column)) {
            searchCondition = product.category.containsIgnoreCase(keyword);
        } else {
            searchCondition = product.isNotNull();
        }

        return queryFactory
                .selectFrom(product)
                .where(searchCondition)
                .fetch();
    }
}
