package com.teamproject.furniture.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.furniture.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import static com.teamproject.furniture.product.model.QProduct.product;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    default List<Product> searchProducts(String column, String keyword, JPAQueryFactory queryFactory) {

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
