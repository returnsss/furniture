package com.teamproject.furniture.member.controller;

import com.teamproject.furniture.member.dtos.MemberCreateDto;
import com.teamproject.furniture.member.dtos.MemberLoginDto;
import com.teamproject.furniture.member.dtos.MemberUpdateDto;
import com.teamproject.furniture.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController { // 기능
    private final MemberService memberService;

    @PostMapping("/api/member")
    public Long joinApi(@RequestBody MemberCreateDto memberCreateDto){ // 회원등록
        return memberService.join(memberCreateDto);
    }

    @PatchMapping("/api/member")
    public void updateApi(@RequestBody MemberUpdateDto memberUpdateDto){ // 회원 정보 수정
        memberService.update(memberUpdateDto);
    }


    /*@GetMapping("/api/login")
    public void loginApi(@RequestBody MemberLoginDto memberLoginDto){ // 로그인
        memberService.login(memberLoginDto);
    }*/








}
