package com.teamproject.furniture.order.repository;

import com.teamproject.furniture.order.dtos.OrderInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderInfoRepositoryCustom {
    Page<OrderInfoDto> selectOrderList(String searchVal, Pageable pageable, Boolean isAdmin);
}
