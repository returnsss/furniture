package com.teamproject.furniture.board.service;

import com.teamproject.furniture.board.dtos.BoardDto;
import com.teamproject.furniture.board.dtos.PageBoardRequestDto;
import com.teamproject.furniture.board.dtos.PageBoardResponseDto;
import com.teamproject.furniture.board.domain.Board;
import com.teamproject.furniture.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    /**
     * 글 등록
     * @param boardDto
     * @return bno
     */
    public Long register(BoardDto boardDto){
        Board board = modelMapper.map(boardDto, Board.class);
        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    /**
     * 글 조회
     * @param bno
     * @return
     */
    public BoardDto readOne(Long bno) {
        Board board = boardRepository.findById(bno).orElseThrow();

        BoardDto boardDto = modelMapper.map(board, BoardDto.class);

        return boardDto;
    }

    /**
     * 글 수정
     * @param boardDto
     */
    public void modify(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBno()).orElseThrow();
        board.change(boardDto.getTitle(), boardDto.getContent());

        boardRepository.save(board);
    }

    /**
     * 글 삭제
     * @param bno
     */
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    /**
     * 목록 / 검색 처리
     * @param pageBoardRequestDto
     * @return
     */
    public PageBoardResponseDto<BoardDto> list(PageBoardRequestDto pageBoardRequestDto) {
        String[] types = pageBoardRequestDto.getTypes();
        String keyword = pageBoardRequestDto.getKeyword();
        Pageable pageable = pageBoardRequestDto.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDto> boardDtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .collect(Collectors.toList());

        return PageBoardResponseDto.<BoardDto>withAll()
                .pageBoardRequestDto(pageBoardRequestDto)
                .boardList(boardDtoList)
                .total((int)result.getTotalElements())
                .build();
    }

}
