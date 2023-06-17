package com.teamproject.furniture.admin.controller;

import com.teamproject.furniture.product.dtos.ProductPageDto;
import com.teamproject.furniture.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final ProductService productService;

    public AdminViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/adminProductList")
    public String adminProductList(String searchVal, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<ProductPageDto> results = productService.selectProductList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 10);
        pageModelPut(results, model);
        return "/admin/adminProducts";
    }


    private void pageModelPut(Page<ProductPageDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());           // 전체 결과의 갯수
        model.addAttribute("size",  results.getPageable().getPageSize());       // 한 페이지에 보여지는 항목의 수
        model.addAttribute("number",  results.getPageable().getPageNumber());   // 현재 페이지의 번호를 반환
    }

}
