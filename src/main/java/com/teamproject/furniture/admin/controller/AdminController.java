package com.teamproject.furniture.admin.controller;

import com.teamproject.furniture.member.service.MemberService;
import com.teamproject.furniture.order.service.OrderService;
import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import com.teamproject.furniture.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final ProductService productService;
    private final MemberService memberService;
    private final OrderService orderService;

    @Autowired
    public AdminController(ProductService productService, MemberService memberService, OrderService orderService) {
        this.productService = productService;
        this.memberService = memberService;
        this.orderService = orderService;
    }

    /**
     * 제품 등록
     * @param addProductDto
     * @return
     */
    @PostMapping("/product")
    public Long addProductApi(@ModelAttribute AddProductDto addProductDto) throws IOException {


        return productService.addProduct(addProductDto);
    }

    /**
     * 제품 수정
     * @param updateProductDto
     */
    @PatchMapping("/product")
    public void updateApi(@ModelAttribute UpdateProductDto updateProductDto) throws IOException {
        productService.updateProduct(updateProductDto);
    }

    /**
     * 제품 삭제
     * @param productId
     */
    @DeleteMapping("/products/{productId}")
    public void deleteApi(@PathVariable Long productId){
        productService.deleteProduct(productId);
    }


    @PatchMapping("/members/{userId}/state")
    public void updateMemberStateApi(@PathVariable String userId, @RequestParam("stateType") String stateType) { // 회원 상태 변경
        memberService.updateMemberState(userId, stateType);
    }

    @PostMapping("/shippingProgress/{orderNum}")
    public void shippingProgress(@PathVariable String orderNum){
        orderService.shippingProgress(orderNum);
    }

}
