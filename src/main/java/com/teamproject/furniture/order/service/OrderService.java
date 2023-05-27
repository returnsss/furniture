package com.teamproject.furniture.order.service;

import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.MemberOrderDto;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
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
    private MemberRepository memberRepository;
    private CartService cartService;

    public OrderService(OrderDataRepository orderDataRepository, OrderInfoRepository orderInfoRepository) {
        this.orderDataRepository = orderDataRepository;
        this.orderInfoRepository = orderInfoRepository;
    }


    /**
     * 주문한사람, 받는사람 정보 입력받아서 db에 저장
     * @param memberOrderDto
     * @param orderInfoDto
     */
    public void addToOrderInfo(MemberOrderDto memberOrderDto, OrderInfoDto orderInfoDto){
        // 유저 정보 가져오기
        Member member = memberRepository.findByUserId(memberOrderDto.getUserId()).orElseThrow(()-> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // 사람 정보 추가
        OrderInfo orderInfo = new OrderInfo(memberOrderDto, orderInfoDto);
        /*orderInfo.setOrderNum(addCartDto.getOrderNum());
        orderInfo.setUserId(memberDto.getUserId());
        orderInfo.setOrderName(memberDto.getName());
        orderInfo.setOrderTel(memberDto.getPhone());
        orderInfo.setOrderEmail(memberDto.getEmail());
        orderInfo.setReceiveName(orderInfoDto.getReceiveName());
        orderInfo.setReceiveTel(orderInfoDto.getReceiveTel());
        orderInfo.setReceiveAddress(orderInfoDto.getReceiveAddress());
        orderInfo.setPayAmount(cartService.cartTotalPrice(member.getUserId()));*/
        //OrderInfo save = orderInfoRepository.save(orderInfo);
        orderInfoRepository.save(orderInfo);
    }



}
