package com.teamproject.furniture.board.controller;

import com.teamproject.furniture.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;



}
