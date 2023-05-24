package com.teamproject.furniture.board.service;

import com.teamproject.furniture.board.dtos.BoardDto;
import com.teamproject.furniture.board.dtos.PageRequestDto;
import com.teamproject.furniture.board.dtos.PageResponseDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){ // 등록 작업
        log.info(boardService.getClass().getName());

        BoardDto boardDto = BoardDto.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();
        Long bno = boardService.register(boardDto);

        log.info("bno : " + bno);
    }

    @Test
    public void testReadOne(){ // 조회
        Long bno = 3L;
        BoardDto boardDto = boardService.readOne(bno);

        log.info(boardDto);
    }

    @Test
    public void testModify(){ // 수정
        // 변경이 필요한 데이터만
        BoardDto boardDto = BoardDto.builder()
                .bno(99L)
                .title("update... 99")
                .content("update content 99")
                .build();

        boardService.modify(boardDto);
    }

    @Test
    public  void testRemove(){ // 삭제
        Long bno = 2L;

        boardService.remove(bno);
    }

    @Test
    public void testList(){ // 검색기능
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDto<BoardDto> responseDto = boardService.list(pageRequestDto);
        log.info(responseDto);
    }
}
