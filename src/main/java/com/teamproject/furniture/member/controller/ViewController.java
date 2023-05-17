package com.teamproject.furniture.member.controller;

import com.teamproject.furniture.member.dtos.MemberPageDto;
import com.teamproject.furniture.member.repository.MemberRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    private final MemberRepositoryCustom memberRepositoryCustom;

    public ViewController(MemberRepositoryCustom memberRepositoryCustom) {
        this.memberRepositoryCustom = memberRepositoryCustom;
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }


    @GetMapping("/members")
    public String list(String searchVal, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<MemberPageDto> results = memberRepositoryCustom.selectMemberList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 10);
        pageModelPut(results, model);
        return "member-list";
    }

    private void pageModelPut(Page<MemberPageDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());           // 전체 결과의 갯수
        model.addAttribute("size",  results.getPageable().getPageSize());       // 한 페이지에 보여지는 항목의 수
        model.addAttribute("number",  results.getPageable().getPageNumber());   // 현재 페이지의 번호를 반환
    }

}
