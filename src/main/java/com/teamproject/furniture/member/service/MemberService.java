package com.teamproject.furniture.member.service;

import com.teamproject.furniture.member.dtos.MemberCreateDto;
import com.teamproject.furniture.member.dtos.MemberLoginDto;
import com.teamproject.furniture.member.dtos.MemberUpdateDto;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     * @param memberCreateDto
     * @return
     */
    public Long join(MemberCreateDto memberCreateDto) {
        validateDuplicateMember(memberCreateDto); // 중복 회원 검증
        String encodedPassword = passwordEncoder.encode(memberCreateDto.getPassword()); // 비밀번호 암호화
        // memberCreateDto를 member로 바꿔야함Member
        Member member = new Member(memberCreateDto);
        member.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        Member save = memberRepository.save(member);
        return save.getMemberId();
    }

    /**
     * 유효성 검사
     * @param memberCreateDto
     */
    private void validateDuplicateMember(MemberCreateDto memberCreateDto) {
        Optional<Member> findMembers = memberRepository.findByUserId(memberCreateDto.getUserId());
        if (findMembers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 회원 정보 수정
     * @param memberUpdateDto
     */
    public void update(MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberUpdateDto.getMemberId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        // member에 update함수를 만들어서 인자값으로 MemberUpdateDto 값이 쓰인다
        member.update(memberUpdateDto);
//        Optional<Member> a = Optional.ofNullable(null);
//        a.orElseThrow(); // 있으면 member던지고 없으면 예외
    }


    /**
     * 로그인
     * @param memberLoginDto
     * @return
     */
    public Optional<Member> login(MemberLoginDto memberLoginDto) {
        Optional<Member> member = memberRepository.findByUserId(memberLoginDto.getUserId());
        /*if (member.isEmpty() || !member.orElseThrow().getPassword().equals(memberLoginDto.getPassword())) {
            throw new IllegalStateException("로그인에 실패하였습니다.");
        }*/
        if (member.isEmpty() || !passwordEncoder.matches(memberLoginDto.getPassword(), member.orElseThrow().getPassword())) {
            throw new IllegalStateException("로그인에 실패하였습니다.");
        }
        return member;
    }


    /**
     * 모든 회원 조회
     * @return
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 회원 정보 상세 보기
     * @param memberId
     * @return
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }



}
