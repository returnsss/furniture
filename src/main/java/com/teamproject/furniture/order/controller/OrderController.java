package com.teamproject.furniture.order.controller;

import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.service.OrderService;
import com.teamproject.furniture.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;


    @PostMapping("/data/add")
    public void addToOrderData(@RequestBody List<OrderDataDto> orderDataList, HttpSession session) throws Exception {

        orderService.addToOrderData(orderDataList, session);
        productService.productLockCnt(orderDataList);
    }

    @PostMapping("/info/add")
    public void addToOrderInfo(@RequestBody OrderInfoDto orderInfoDto, HttpSession session) {
        orderService.addToOrderInfo(orderInfoDto, session);
    }

    @GetMapping("/{orderNum}")
    public OrderInfoDto getOrderInfoDto(@PathVariable String orderNum){
        return orderService.getOrderInfoDto(orderNum);
    }

}
