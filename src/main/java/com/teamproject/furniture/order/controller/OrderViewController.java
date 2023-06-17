package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.UserDto;
import com.teamproject.furniture.order.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderViewController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderViewController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/orderInfo")
    public String addOrderInfo(@AuthenticationPrincipal UserDto userDto, Model model){
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
        return "/order/orderInfo";
    }

}
