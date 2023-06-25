package com.teamproject.furniture.product.controller;

import com.teamproject.furniture.product.dtos.ProductPageDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/{productId}")
    public String product(@PathVariable Long productId, Model model){
        Product product = productService.getProduct(productId);

        model.addAttribute("product", product);

        return "/product/product";
    }

    @GetMapping("/list")
    public String list(String searchVal, @PageableDefault(size = 6) Pageable pageable, Model model){
        Page<ProductPageDto> results = productService.selectProductList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 10);
        pageModelPut(results, model);
        return "/product/products-list";
    }

    private void pageModelPut(Page<ProductPageDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());           // 전체 결과의 갯수
        model.addAttribute("size",  results.getPageable().getPageSize());       // 한 페이지에 보여지는 항목의 수
        model.addAttribute("number",  results.getPageable().getPageNumber());   // 현재 페이지의 번호를 반환
    }

}
