package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.MemberDto;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.service.MemberService;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import com.teamproject.furniture.util.UserUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderViewController {

    private final OrderService orderService;
    private final CartService cartService;
    private final MemberService memberService;

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

        MemberDto member = memberService.findUser(userId);

        model.addAttribute("cartDtoList", cartDtoList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", member);

        return "/order/orderInfo";
    }

    @GetMapping("/payInfo")
    public String payInfo(Model model, HttpSession session) throws Exception {
        String orderNum = orderService.getOrderNum(session);
        OrderInfoDto orderInfoDto = orderService.getOrderInfoDto(orderNum);
        String orderProductName = orderService.getProductName(orderNum);

        model.addAttribute("info", orderInfoDto);
        model.addAttribute("orderProductName", orderProductName);

        return "/order/payInfo";
    }

    @GetMapping("/success")
    public void success(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception{
        orderService.processSuccess(req, session);
        res.sendRedirect("/order/orderDone");
    }

    @GetMapping("/orderDone")
    public String orderDone(Model model, HttpSession session){
        String userId = UserUtil.getUserId();
        cartService.clearCart(userId);

        String orderNum = orderService.getOrderNum(session);

        List<OrderDataDto> orderDataDtoList = orderService.getOrderDatas(session);
        OrderInfoDto orderInfoDto = orderService.getOrderInfoDto(orderNum);

        model.addAttribute("orderDataDtoList", orderDataDtoList);
        model.addAttribute("orderInfoDto", orderInfoDto);

        return "/order/orderDone";
    }




}
