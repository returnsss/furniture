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

        memberCreateDto.setBirth(memberCreateDto.getBirthyy() + "/" + memberCreateDto.getBirthmm() + "/" + memberCreateDto.getBirthdd());
        memberCreateDto.setEmail(memberCreateDto.getMail1() + "@" + memberCreateDto.getMail2());
        memberCreateDto.setAddress(memberCreateDto.getZipcode() + "/" + memberCreateDto.getAddress1() + "/" + memberCreateDto.getAddress2());
        memberCreateDto.setPhone(memberCreateDto.getPhone1() + "-" + memberCreateDto.getPhone2() + "-" + memberCreateDto.getPhone3());


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

    public boolean validate(String userId){
        Optional<Member> findMembers = memberRepository.findByUserId(userId);
        return findMembers.isPresent();
    }


    /**
     * 회원 정보 수정
     * @param memberUpdateDto
     */
    public void update(MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findByUserId(memberUpdateDto.getUserId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        String encodedPassword = passwordEncoder.encode(memberUpdateDto.getPassword()); // 비밀번호 암호화

        memberUpdateDto.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        memberUpdateDto.setState(STATE_USER);

        memberUpdateDto.setMemberId(member.getMemberId());
        memberUpdateDto.setBirth(memberUpdateDto.getBirthyy() + "/" + memberUpdateDto.getBirthmm() + "/" + memberUpdateDto.getBirthdd());
        memberUpdateDto.setEmail(memberUpdateDto.getMail1() + "@" + memberUpdateDto.getMail2());
        memberUpdateDto.setAddress(memberUpdateDto.getZipcode() + "/" + memberUpdateDto.getAddress1() + "/" + memberUpdateDto.getAddress2());
        memberUpdateDto.setPhone(memberUpdateDto.getPhone1() + "-" + memberUpdateDto.getPhone2() + "-" + memberUpdateDto.getPhone3());


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

    public MemberDto findUser(String userId){
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .userId(member.getUserId())
                .password(member.getPassword())
                .name(member.getName())
                .birth(member.getBirth())
                .gender(member.getGender())
                .email(member.getEmail())
                .address(member.getAddress())
                .phone(member.getPhone())
                .receiveMail(member.getReceiveMail())
                .receivePhone(member.getReceivePhone())
                .agreement(member.getAgreement())
                .build();
    }


    public MemberDto getMember(Long memberId){
        Member member = findOne(memberId);
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .userId(member.getUserId())
                .password(member.getPassword())
                .name(member.getName())
                .birth(member.getBirth())
                .gender(member.getGender())
                .email(member.getEmail())
                .address(member.getAddress())
                .phone(member.getPhone())
                .receiveMail(member.getReceiveMail())
                .receivePhone(member.getReceivePhone())
                .agreement(member.getAgreement())
                .build();
    }




    public Page<MemberPageDto> selectMemberList(String searchVal, Pageable pageable) {
        return memberRepositoryCustom.selectMemberList(searchVal, pageable);
    }


    public void updateMemberState(String userId, String stateType){
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        Long memberId = member.getMemberId();

        switch (stateType) {
            case "STATE_USER":
                member.updateStateUser(memberId);
                break;
            case "STATE_LIMIT":
                member.updateStateLimit(memberId);
                break;
            case "STATE_WITHDRAWAL":
                member.updateStateWithdrawal(memberId);
                break;
            case "STATE_ADMIN":
                member.updateStateAdmin(memberId);
                break;
            default:
                break;
        }
    }


}
