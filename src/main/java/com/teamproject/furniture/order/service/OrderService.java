package com.teamproject.furniture.order.service;

import com.teamproject.furniture.order.domain.OrderInfo;
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


    /**
     * 주문한사람, 받는사람 정보 입력받아서 db에 저장
     * @param orderInfoDto
     * @param orderInfoDto
     */
    public void addToOrderInfo(OrderInfoDto orderInfoDto){

        OrderInfo orderInfo = new OrderInfo(orderInfoDto);

        orderInfoRepository.save(orderInfo);
    }



}
