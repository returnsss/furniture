package com.teamproject.furniture.product.controller;

import com.teamproject.furniture.product.dtos.AddProductDto;
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
    public Long addProductApi(@RequestBody AddProductDto addProductDto){
        return productService.addProduct(addProductDto);
    }

    // 제품조회는 @RequestParam 사용해서
    @GetMapping("/api/products/{productId}")
    public Product getProductApi(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @GetMapping("/api/products")
    public List<Product> getProductsApi(){
        return productService.getProducts();
    }

}
