package com.teamproject.furniture.order.repository;

import com.teamproject.furniture.order.domain.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, Long> {
    @Modifying
    @Query(value = "delete from order_data od WHERE od.orderNum = :orderNum", nativeQuery = true)
    void clearOrderData(String orderNum);

    List<OrderData> findByOrderNum(String orderNum);
}
