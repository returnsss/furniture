package com.teamproject.furniture.board.controller;

import com.teamproject.furniture.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController { //todo 금일 작업 예정

    private final BoardService boardService;



}
