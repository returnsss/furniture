package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/data/add")
    public void addToOrderData(@RequestBody OrderDataDto orderDataDto){
        orderService.addToOrderData(orderDataDto);
    }

    @PostMapping("/info/add")
    public void addToOrderInfo(@RequestBody OrderInfoDto orderInfoDto) {
        orderService.addToOrderInfo(orderInfoDto);
    }

    @GetMapping("/{orderNo}")
    private OrderInfoDto getOrderInfoDto(@PathVariable Long orderNo){
        return orderService.getOrderInfoDto(orderNo);
    }

    @PostMapping("/{orderNo}")
    public void updateOrderStep(@PathVariable Long orderNo){
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 사용자 인증정보 가져오기
        String currentUserId = authentication.getName(); // 현재 사용자의 아이디 가져오기

        if(!currentUserId.equals(userId)){
            throw new IllegalStateException("사용자 아이디가 다릅니다.");
        }*/
        orderService.updateOrderStep(orderNo);
    }

}
