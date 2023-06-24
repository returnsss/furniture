package com.teamproject.furniture.member.controller;

import com.teamproject.furniture.member.dtos.MemberDto;
import com.teamproject.furniture.member.dtos.MemberPageDto;
import com.teamproject.furniture.member.dtos.UserDto;
import com.teamproject.furniture.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
public class ViewController {

    private final MemberService memberService;

    public ViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login_form";
    }


    @GetMapping("/addmember")
    public String addMember(){
        return "/member/addMember";
    }

    @GetMapping("/updateMember")
    public String updateMember(@AuthenticationPrincipal UserDto userDto, Model model){
        Long memberId = userDto.getMemberId();
        log.info(memberId);
        MemberDto memberDto = memberService.getMember(memberId);
        log.info(memberDto);
        model.addAttribute("memberDto", memberDto);

        return "/member/updateMember";
    }

    @GetMapping("/resultMember")
    public String result(){
        return "/member/resultMember";
    }

    @GetMapping("/loginFailed")
    public String loginFailed(){
        return "/member/loginFailed";
    }

}
