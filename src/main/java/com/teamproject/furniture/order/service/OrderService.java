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

        orderInfoDto.setOrderStep("ORDER_FAIL");
        OrderInfo orderInfo = new OrderInfo(orderInfoDto);

        orderInfoRepository.save(orderInfo);
    }

    public OrderInfoDto getOrderInfoDto(Long orderNo){
        OrderInfo orderInfo = orderInfoRepository.findById(orderNo).orElseThrow();
        return OrderInfoDto.builder()
                .orderNo(orderInfo.getOrderNo())
                .userId(orderInfo.getUserId())
                .orderName(orderInfo.getOrderName())
                .orderTel(orderInfo.getOrderTel())
                .orderEmail(orderInfo.getOrderEmail())
                .receiveName(orderInfo.getReceiveName())
                .receiveTel(orderInfo.getReceiveTel())
                .receiveAddress(orderInfo.getReceiveAddress())
                .orderStep(orderInfo.getOrderStep())
                .payAmount(orderInfo.getPayAmount())
                .orderDate(orderInfo.getOrderDate())
                .payDate(orderInfo.getPayDate())
                .build();

    }

    public void updateOrderStep(Long orderNo, String userId){
        OrderInfo orderInfo = orderInfoRepository.findById(orderNo).orElseThrow(() -> new IllegalStateException("존재하지 않는 orderNo입니다."));

        if(!orderInfo.getUserId().equals(userId)){
            throw new IllegalStateException("userId가 일치하지 않아서 실행 할 수 없습니다.");
        }
        orderInfo.setOrderStep("ORDER_SUCCESS");
        orderInfoRepository.save(orderInfo);
    }



}
