package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.member.dtos.MemberOrderDto;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.service.MemberService;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable Long memberId){ // 회원 정보 조회
        return memberService.findOne(memberId);
    }


    /*@PostMapping("/{memberId}")
    public void placeOrder(@PathVariable Long memberId,
                           @RequestBody MemberDto memberDto,
                           @RequestBody OrderInfoDto orderInfoDto,
                           @RequestBody AddCartDto addCartDto) {

        orderService.addToOrderInfo(memberDto, orderInfoDto, addCartDto);

    }*/
    @PostMapping("/add")
    public void addToOrderInfo(
            @RequestBody MemberOrderDto memberOrderDto,
            @RequestBody OrderInfoDto orderInfoDto
    ) {
        orderService.addToOrderInfo(memberOrderDto, orderInfoDto);
    }


}
