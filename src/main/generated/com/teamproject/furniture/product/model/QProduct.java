package com.teamproject.furniture.product.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1928528528L;

    public static final QProduct product = new QProduct("product");

    public final StringPath category = createString("category");

    public final StringPath description = createString("description");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productPrice = createNumber("productPrice", Integer.class);

    public final NumberPath<Integer> productsInStock = createNumber("productsInStock", Integer.class);

    public final StringPath registDay = createString("registDay");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

