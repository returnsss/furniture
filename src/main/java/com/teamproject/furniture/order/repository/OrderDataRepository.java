package com.teamproject.furniture.order.repository;

import com.teamproject.furniture.order.domain.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, Long> {
}
