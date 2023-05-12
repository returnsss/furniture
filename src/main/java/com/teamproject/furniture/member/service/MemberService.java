package com.teamproject.furniture.member.service;

import com.teamproject.furniture.member.dtos.MemberCreateDto;
import com.teamproject.furniture.member.dtos.MemberLoginDto;
import com.teamproject.furniture.member.dtos.MemberUpdateDto;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(MemberCreateDto memberCreateDto) { // 등록
        validateDuplicateMember(memberCreateDto); // 중복 회원 검증
        // memberCreateDto를 member로 바꿔야함Member
        Member member = new Member(memberCreateDto);
        member = memberRepository.save(member);
        return member.getMemberId();
    }

    private void validateDuplicateMember(MemberCreateDto memberCreateDto) { // 유효성 검사
        Optional<Member> findMembers = memberRepository.findByUserId(memberCreateDto.getUserId());
        if (findMembers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    public void update(MemberUpdateDto memberUpdateDto) { // 회원 정보 수정
        Member member = memberRepository.findById(memberUpdateDto.getMemberId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        // member에 update함수를 만들어서 인자값으로 MemberUpdateDto 값이 쓰인다
        member.update(memberUpdateDto);
//        Optional<Member> a = Optional.ofNullable(null);
//        a.orElseThrow(); // 있으면 member던지고 없으면 예외
    }


    public Optional<Member> login(MemberLoginDto memberLoginDto) { // 로그인
        Optional<Member> member = memberRepository.findByUserId(memberLoginDto.getUserId());
        if (member.isEmpty() || !member.orElseThrow().getPassword().equals(memberLoginDto.getPassword())) {
            throw new IllegalStateException("로그인에 실패하였습니다.");
        }
        return member;
    }



    public List<Member> findAll() { // 모든 회원 조회
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) { // 회원 정보 상세 보기
        return memberRepository.findById(memberId);
    }



}
