package com.teamproject.furniture.member.controller;

import com.teamproject.furniture.member.dtos.MemberCreateDto;
import com.teamproject.furniture.member.dtos.MemberLoginDto;
import com.teamproject.furniture.member.dtos.MemberUpdateDto;
import com.teamproject.furniture.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController { // 기능
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/api/member")
    public Long joinApi(@RequestBody MemberCreateDto memberCreateDto){
        return memberService.join(memberCreateDto);
    }

    @PatchMapping("/api/member")
    public void updateApi(@RequestBody MemberUpdateDto memberUpdateDto){
        memberService.update(memberUpdateDto);
    }


    @GetMapping("/api/login")
    public void loginApi(@RequestBody MemberLoginDto memberLoginDto){
        memberService.login(memberLoginDto);
    }






}