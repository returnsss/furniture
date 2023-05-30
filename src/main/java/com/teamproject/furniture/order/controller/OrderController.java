package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/orderdata/add")
    public void addToOrderData(@RequestBody OrderDataDto orderDataDto){
        orderService.addToOrderData(orderDataDto);
    }

    @PostMapping("/orderinfo/add")
    public void addToOrderInfo(@RequestBody OrderInfoDto orderInfoDto) {
        orderService.addToOrderInfo(orderInfoDto);
    }

    @GetMapping("/{orderNo}")
    private OrderInfoDto getOrderInfoDto(@PathVariable Long orderNo){
        return orderService.getOrderInfoDto(orderNo);
    }


}
