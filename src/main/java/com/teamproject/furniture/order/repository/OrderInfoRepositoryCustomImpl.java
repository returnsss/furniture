package com.teamproject.furniture.order.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.furniture.member.dtos.MemberPageDto;
import com.teamproject.furniture.member.dtos.QMemberPageDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.dtos.OrderStep;
import com.teamproject.furniture.order.dtos.QOrderInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.teamproject.furniture.member.model.QMember.member;
import static com.teamproject.furniture.order.domain.QOrderInfo.orderInfo;

@Repository
public class OrderInfoRepositoryCustomImpl implements OrderInfoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public OrderInfoRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<OrderInfoDto> selectOrderList(String searchVal, Pageable pageable) {
        List<OrderInfoDto> content = getOrderInfoDtos(searchVal, pageable);
        Long count = getCount(searchVal);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchVal) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (searchVal != null && !searchVal.isEmpty()) {
            whereClause.and(orderInfo.orderNum.containsIgnoreCase(searchVal));
        }
        whereClause.and(orderInfo.orderStep.ne(OrderStep.ORDER_FAIL));

        Long count = queryFactory
                .select(orderInfo.count())
                .from(orderInfo)
                .where(whereClause)
                .fetchOne();
        return count;
    }


    private List<OrderInfoDto> getOrderInfoDtos(String searchVal, Pageable pageable) {

        BooleanBuilder whereClause = new BooleanBuilder();

        if (searchVal != null && !searchVal.isEmpty()) {
            whereClause.and(orderInfo.orderNum.containsIgnoreCase(searchVal));
        }

        whereClause.and(orderInfo.orderStep.ne(OrderStep.ORDER_FAIL));

        List<OrderInfoDto> content = queryFactory
                .select(new QOrderInfoDto(
                        orderInfo.orderNum
                        ,orderInfo.userId
                        ,orderInfo.orderName
                        ,orderInfo.orderTel
                        ,orderInfo.orderEmail
                        ,orderInfo.receiveName
                        ,orderInfo.receiveTel
                        ,orderInfo.receiveAddress
                        ,orderInfo.orderStep
                        ,orderInfo.payAmount
                        ,orderInfo.orderDate
                        ,orderInfo.payDate))
                .from(orderInfo)
                .where(whereClause)
                .orderBy(orderInfo.orderNum.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
        return content;
    }

}
