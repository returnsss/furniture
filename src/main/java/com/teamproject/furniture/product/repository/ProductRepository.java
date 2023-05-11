package com.teamproject.furniture.product.repository;

import com.teamproject.furniture.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();    // 모든 제품 조회

}
