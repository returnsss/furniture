package com.teamproject.furniture.product.controller;

import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/product")
    public Long addProductApi(@RequestBody AddProductDto addProductDto){ // 제품 등록
        return productService.addProduct(addProductDto);
    }

    // 제품조회는 @RequestParam 사용해서
    @GetMapping("/api/products/{productId}")
    public Product getProductApi(@PathVariable Long productId){ // 제품 상세보기
        return productService.getProduct(productId);
    }

    @GetMapping("/api/products")
    public List<Product> getProductsApi(){ // 모든 제품 출력
        return productService.getProducts();
    }

    @PatchMapping("/api/product")
    public void updateApi(@RequestBody UpdateProductDto updateProductDto){ // 제품 수정
        productService.updateProduct(updateProductDto);
    }


    @DeleteMapping("/api/products/{productId}")
    public void deleteApi(@PathVariable Long productId){ // 제품 삭제
        productService.deleteProduct(productId);
    }


    @GetMapping("/api/products/search")
    public List<Product> searchProducts(@RequestParam String column, @RequestParam String keyword) { // 제품 검색
        return productService.searchProducts(column, keyword);
    }


}
