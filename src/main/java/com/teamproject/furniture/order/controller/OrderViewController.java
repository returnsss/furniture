package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.board.dtos.BoardDto;
import com.teamproject.furniture.board.dtos.PageRequestDto;
import com.teamproject.furniture.board.dtos.PageResponseDto;
import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.MemberDto;
import com.teamproject.furniture.member.dtos.MemberPageDto;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.service.MemberService;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import com.teamproject.furniture.util.UserUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

        session.removeAttribute("orderNum");

        return "/order/orderDone";
    }


    @GetMapping("/myList")
    public String list(String searchVal, @PageableDefault(size = 5) Pageable pageable, Model model){
        Boolean isAdmin = false;
        Page<OrderInfoDto> results = orderService.selectOrderList(searchVal, pageable, isAdmin);

        model.addAttribute("list", results);
        model.addAttribute("maxPage", 10);
        pageModelPutOrderInfo(results, model);

        return "/mypage/orderList";
    }

    private void pageModelPutOrderInfo(Page<OrderInfoDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());           // 전체 결과의 갯수
        model.addAttribute("size",  results.getPageable().getPageSize());       // 한 페이지에 보여지는 항목의 수
        model.addAttribute("number",  results.getPageable().getPageNumber());   // 현재 페이지의 번호를 반환
    }


}
