package com.teamproject.furniture.order.service;

import com.teamproject.furniture.order.domain.OrderData;
import com.teamproject.furniture.order.domain.OrderInfo;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.repository.OrderDataRepository;
import com.teamproject.furniture.order.repository.OrderInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private OrderDataRepository orderDataRepository;
    private OrderInfoRepository orderInfoRepository;

    public OrderService(OrderDataRepository orderDataRepository, OrderInfoRepository orderInfoRepository) {
        this.orderDataRepository = orderDataRepository;
        this.orderInfoRepository = orderInfoRepository;
    }

    public void addToOrderData(OrderDataDto orderDataDto, HttpSession session){
        String orderNum = getOrderNum(session);
        List<OrderData> orderDataExist = orderDataRepository.findByOrderNum(orderNum);
        if (orderDataExist != null){
            orderDataRepository.deleteOrderDataByOrderNum(orderNum);
        }

        OrderData orderData = new OrderData(orderDataDto);
        orderData.setOrderNum(orderNum);

        orderDataRepository.save(orderData);
    }





    public String getOrderNum(HttpSession session){
        String orderNum = (String) session.getAttribute("orderNum");
        if(orderNum == null){
            orderNum = generateOrderNum();
            session.setAttribute("orderNum", orderNum);
        }
        return orderNum;
    }

    private String generateOrderNum(){
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        return nowStr + "-" + sessionId;
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

    public void updateOrderStep(Long orderNo){
        OrderInfo orderInfo = orderInfoRepository.findById(orderNo).orElseThrow(() -> new IllegalStateException("존재하지 않는 orderNo입니다."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 사용자 인증정보 가져오기
        String currentUserId = authentication.getName(); // 현재 사용자의 아이디 가져오기

        if(!orderInfo.getUserId().equals(currentUserId)){
            throw new IllegalStateException("userId가 일치하지 않아서 실행 할 수 없습니다.");
        }
        orderInfo.setOrderStep("ORDER_SUCCESS");
        orderInfoRepository.save(orderInfo);
    }



}
