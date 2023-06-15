package com.teamproject.furniture.cart.controller;

import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.UserDto;
import com.teamproject.furniture.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
@Log4j2
@RequiredArgsConstructor
public class CartViewController {

    private final CartService cartService;

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal UserDto userDto, Model model) { // 화면에 목록 데이터를 출력
        String userId = userDto.getUserId();
        List<CartDto> cartDtoList = cartService.getCartItems(userId);

        int thisPrice;
        int totalPrice = 0;

        for (CartDto cartDto : cartDtoList) {
            thisPrice = cartDto.getProductPrice() * cartDto.getCnt();
            totalPrice += thisPrice;
        }
        
        model.addAttribute("cartDtoList", cartDtoList);
        model.addAttribute("totalPrice", totalPrice);
        return "/mypage/cart";
    }

}
