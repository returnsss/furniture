package com.teamproject.furniture.member.repository;

import com.teamproject.furniture.member.dtos.MemberPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<MemberPageDto> selectMemberList(String searchVal, Pageable pageable);
}
