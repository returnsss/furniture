package com.teamproject.furniture.order.repository;

import com.teamproject.furniture.order.domain.OrderData;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, Long> {

    void deleteOrderDataByOrderNum(String orderNum);

    List<OrderData> findByOrderNum(String orderNum);

    List<OrderData> findByOrderNumIn(Collection<String> orderNums);
}
