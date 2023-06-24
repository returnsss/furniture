package com.teamproject.furniture.product.controller;

import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * 제품 상세보기
     * @param productId
     * @return
     */
    @GetMapping("/api/products/{productId}")
    public Product getProductApi(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    /**
     * 모든 제품 출력
     * @return
     */
    @GetMapping("/api/products")
    public List<Product> getProductsApi(){
        return productService.getProducts();
    }



    /**
     * 제품 검색
     * @param column
     * @param keyword
     * @return
     */
    @GetMapping("/api/products/search")
    public List<Product> searchProducts(@RequestParam String column, @RequestParam String keyword) {
        return productService.searchProducts(column, keyword);
    }


}
