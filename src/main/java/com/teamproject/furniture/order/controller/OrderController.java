package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/data/add")
    public void addToOrderData(@RequestBody OrderDataDto orderDataDto, HttpSession session){

        orderService.addToOrderData(orderDataDto, session);
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
        orderService.updateOrderStep(orderNo);
    }

}
