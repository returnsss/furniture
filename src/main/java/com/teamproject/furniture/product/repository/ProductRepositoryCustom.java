package com.teamproject.furniture.product.repository;

import com.teamproject.furniture.product.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchProducts(String column, String keyword);
}
