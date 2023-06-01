package com.teamproject.furniture.order.service;

import com.teamproject.furniture.order.domain.OrderData;
import com.teamproject.furniture.order.domain.OrderInfo;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.repository.OrderDataRepository;
import com.teamproject.furniture.order.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private OrderDataRepository orderDataRepository;
    private OrderInfoRepository orderInfoRepository;

    public OrderService(OrderDataRepository orderDataRepository, OrderInfoRepository orderInfoRepository) {
        this.orderDataRepository = orderDataRepository;
        this.orderInfoRepository = orderInfoRepository;
    }

    public void addToOrderData(OrderDataDto orderDataDto){
        OrderData orderData = new OrderData(orderDataDto);

        orderDataRepository.save(orderData);
    }


    public void addToOrderInfo(OrderInfoDto orderInfoDto){

        OrderInfo orderInfo = new OrderInfo(orderInfoDto);

        orderInfoRepository.save(orderInfo);
    }

    public OrderInfoDto getOrderInfoDto(Long orderNo){
        OrderInfo orderInfo = orderInfoRepository.findById(orderNo).orElseThrow();
        return OrderInfoDto.builder()
                .userId(orderInfo.getUserId())
                .orderName(orderInfo.getOrderName())
                .orderTel(orderInfo.getOrderTel())
                .orderEmail(orderInfo.getOrderEmail())
                .receiveName(orderInfo.getReceiveName())
                .receiveTel(orderInfo.getReceiveTel())
                .receiveAddress(orderInfo.getReceiveAddress())
                .payAmount(orderInfo.getPayAmount())
                .build();

    }



}
