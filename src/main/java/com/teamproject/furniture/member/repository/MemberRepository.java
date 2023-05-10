package com.teamproject.furniture.member.repository;

import com.teamproject.furniture.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //Member save(Member member);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByName(String name);

    List<Member> findAll();

}
