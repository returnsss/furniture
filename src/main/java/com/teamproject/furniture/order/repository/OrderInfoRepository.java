package com.teamproject.furniture.order.repository;

import com.teamproject.furniture.order.domain.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, String> {
    void deleteOrderInfoByOrderNum(String orderNum);
}
