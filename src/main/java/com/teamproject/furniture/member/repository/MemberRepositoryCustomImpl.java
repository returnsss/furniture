package com.teamproject.furniture.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.furniture.member.dtos.MemberPageDto;
import com.teamproject.furniture.member.dtos.QMemberPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.teamproject.furniture.member.model.QMember.member;

import java.util.List;

@Repository
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<MemberPageDto> selectMemberList(String searchVal, Pageable pageable) {
        List<MemberPageDto> content = getMemberDtos(searchVal, pageable);
        Long count = getCount(searchVal);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchVal) {
        Long count = queryFactory
                .select(member.count())
                .from(member)
                .fetchOne();
        return count;
    }

    private List<MemberPageDto> getMemberDtos(String searchVal, Pageable pageable) {
        List<MemberPageDto> content = queryFactory
                .select(new QMemberPageDto(
                        member.userId
                        ,member.name
                        ,member.birth
                        ,member.gender
                        ,member.email
                        ,member.address
                        ,member.phone
                        ,member.state))
                .from(member)
                .orderBy(member.name.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
        return content;
    }

}
