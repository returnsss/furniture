package com.teamproject.furniture.product.repository;

import com.teamproject.furniture.product.dtos.ProductPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustomPage {
    Page<ProductPageDto> selectProductList(String searchVal, Pageable pageable);
}
