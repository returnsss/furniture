package com.teamproject.furniture.product.repository;

import com.teamproject.furniture.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
