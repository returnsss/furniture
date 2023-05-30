package com.teamproject.furniture.member.service;

import com.teamproject.furniture.member.dtos.*;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
import com.teamproject.furniture.member.repository.MemberRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.teamproject.furniture.member.model.Member.STATE_USER;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepositoryCustom memberRepositoryCustom;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, MemberRepositoryCustom memberRepositoryCustom) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.memberRepositoryCustom = memberRepositoryCustom;
    }

    /**
     * 회원가입
     * @param memberCreateDto
     * @return
     */
    public Long join(MemberCreateDto memberCreateDto) {
        validateDuplicateMember(memberCreateDto); // 중복 회원 검증
        String encodedPassword = passwordEncoder.encode(memberCreateDto.getPassword()); // 비밀번호 암호화

        memberCreateDto.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        memberCreateDto.setState(STATE_USER);

        // memberCreateDto를 member로 바꿔야함Member
        Member member = new Member(memberCreateDto);

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

        member.update(memberUpdateDto);
    }


    /**
     * 로그인
     *
     * @param memberLoginDto
     * @return
     */
    public MemberLoginDto login(MemberLoginDto memberLoginDto) {
        Optional<Member> member = memberRepository.findByUserId(memberLoginDto.getUserId());
        if (member.isEmpty() || !passwordEncoder.matches(memberLoginDto.getPassword(), member.get().getPassword())) {
            throw new IllegalStateException("로그인에 실패하였습니다.");
        }
        return memberLoginDto;
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
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
    }


    public MemberDto getMember(Long memberId){
        Member member = findOne(memberId);
        return MemberDto.builder()
                .userId(member.getUserId())
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }




    public Page<MemberPageDto> selectMemberList(String searchVal, Pageable pageable) {
        return memberRepositoryCustom.selectMemberList(searchVal, pageable);
    }


    public void updateMemberState(Long memberId, String stateType){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        switch (stateType) {
            case "user":
                member.updateStateUser(memberId);
                break;
            case "report":
                member.updateStateReport(memberId);
                break;
            case "withdrawal":
                member.updateStateWithdrawal(memberId);
                break;
            case "admin":
                member.updateStateAdmin(memberId);
                break;
            default:
                break;
        }
    }


}
