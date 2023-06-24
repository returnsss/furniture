package com.teamproject.furniture.admin.controller;

import com.teamproject.furniture.member.dtos.MemberPageDto;
import com.teamproject.furniture.member.service.MemberService;
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
@RequestMapping("/admin")
public class AdminViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public AdminViewController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }


    @GetMapping("/addproduct")
    public String addProduct(){
        return "/admin/addProduct";
    }

    @GetMapping("/updateProduct/{productId}")
    public String updateProduct(@PathVariable Long productId, Model model){
        Product product = productService.getProduct(productId);

        model.addAttribute("product", product);
        return "/admin/updateProduct";
    }


    @GetMapping("/products")
    public String adminProductList(String searchVal, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<ProductPageDto> results = productService.selectProductList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 10);
        pageModelPutProduct(results, model);
        return "/admin/adminProducts";
    }

    @GetMapping("/members")
    public String list(String searchVal, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<MemberPageDto> results = memberService.selectMemberList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 10);
        pageModelPutMember(results, model);
        return "/admin/member-list";
    }


    private void pageModelPutProduct(Page<ProductPageDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());           // 전체 결과의 갯수
        model.addAttribute("size",  results.getPageable().getPageSize());       // 한 페이지에 보여지는 항목의 수
        model.addAttribute("number",  results.getPageable().getPageNumber());   // 현재 페이지의 번호를 반환
    }

    private void pageModelPutMember(Page<MemberPageDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());           // 전체 결과의 갯수
        model.addAttribute("size",  results.getPageable().getPageSize());       // 한 페이지에 보여지는 항목의 수
        model.addAttribute("number",  results.getPageable().getPageNumber());   // 현재 페이지의 번호를 반환
    }

}
