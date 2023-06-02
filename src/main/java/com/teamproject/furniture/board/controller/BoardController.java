package com.teamproject.furniture.board.controller;

import com.teamproject.furniture.board.dtos.BoardDto;
import com.teamproject.furniture.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController { //todo 금일 작업 예정

    private final BoardService boardService;

    @GetMapping("/{writer}")
    public List<BoardDto> getMyBoards(@PathVariable String writer){
        List<BoardDto> myBoards = boardService.getMyBoards(writer);
        return myBoards;
    }


}
