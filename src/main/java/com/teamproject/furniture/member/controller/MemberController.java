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

    @PatchMapping("/api/members-user/{memberId}")
    public void updateStateUserApi(@PathVariable Long memberId){
        memberService.updateStateUser(memberId);
    }
    @PatchMapping("/api/members-report/{memberId}")
    public void updateStateReportApi(@PathVariable Long memberId){
        memberService.updateStateReport(memberId);
    }
    @PatchMapping("/api/members-withdrawal/{memberId}")
    public void updateStateWithdrawalApi(@PathVariable Long memberId){
        memberService.updateStateWithdrawal(memberId);
    }
    @PatchMapping("/api/members-admin/{memberId}")
    public void updateStateAdminApi(@PathVariable Long memberId){
        memberService.updateStateAdmin(memberId);
    }

    @PatchMapping("/api/members/{memberId}/state")
    public void updateMemberStateApi(@PathVariable Long memberId, @RequestParam("type") String type) {
        switch (type) {
            case "user":
                memberService.updateStateUser(memberId);
                break;
            case "report":
                memberService.updateStateReport(memberId);
                break;
            case "withdrawal":
                memberService.updateStateWithdrawal(memberId);
                break;
            case "admin":
                memberService.updateStateAdmin(memberId);
                break;
            default:
                break;
        }
    }

    








}
