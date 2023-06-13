package com.teamproject.furniture.board.controller;

import com.teamproject.furniture.board.dtos.BoardDto;
import com.teamproject.furniture.board.dtos.PageRequestDto;
import com.teamproject.furniture.board.dtos.PageResponseDto;
import com.teamproject.furniture.board.service.BoardService;
import com.teamproject.furniture.member.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model){ // 화면에 목록 데이터를 출력
        PageResponseDto<BoardDto> responseDto = boardService.list(pageRequestDto);

        model.addAttribute("responseDto", responseDto);
    }

    @GetMapping("/mypost")
    public String myPost(@AuthenticationPrincipal UserDto userDto, PageRequestDto pageRequestDto, Model model){ // 화면에 목록 데이터를 출력
        String userId = userDto.getUserId();
        PageResponseDto<BoardDto> responseDto = boardService.myPost(userId, pageRequestDto);

        model.addAttribute("responseDto", responseDto);
        return "/mypage/myPost";
    }


    @GetMapping("/register")
    public void registerGET(@AuthenticationPrincipal UserDto userDto, Model model){
        String userId = userDto.getUserId();
        model.addAttribute("userId", userId);
    }


    @PostMapping("/register")
    public String registerPOST(@Valid BoardDto boardDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("board POST register...");

        if(bindingResult.hasErrors()){
            log.info("has errors...");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info(boardDto);
        Long bno = boardService.register(boardDto);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";

    }

    @GetMapping({"/read", "/modify"})
    public void read(@AuthenticationPrincipal UserDto userDto, Long bno, PageRequestDto pageRequestDto, Model model){
        BoardDto boardDto = boardService.readOne(bno);
        String userId = userDto.getUserId();
        log.info(boardDto);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("userId", userId);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDto pageRequestDto,
                         @Valid BoardDto boardDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log. info("board modify post......" + boardDto);

        if (bindingResult.hasErrors()) {

            log.info("has errors.......");

            String link = pageRequestDto.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDto.getBno());

            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDto);

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDto.getBno());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        log.info("remove post..." + bno);

        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    }


}
