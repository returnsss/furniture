package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.service.MemberService;
import com.teamproject.furniture.order.service.OrderService;
import com.teamproject.furniture.util.UserUtil;
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
    private final MemberService memberService;

    public OrderViewController(OrderService orderService, CartService cartService, MemberService memberService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/Info")
    public String getOrderInfo(Model model){
        String userId = UserUtil.getUserId();
        List<CartDto> cartDtoList = cartService.getCartItems(userId);

        int thisPrice;
        int totalPrice = 0;

        for (CartDto cartDto : cartDtoList) {
            thisPrice = cartDto.getProductPrice() * cartDto.getCnt();
            totalPrice += thisPrice;
        }

        Member member = memberService.findUser(userId);

        model.addAttribute("cartDtoList", cartDtoList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", member);

        return "/order/orderInfo";
    }

}
