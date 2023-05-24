package com.teamproject.furniture.board.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageBoardResponseDto<E> {
    private int page;
    private int size;
    private int total;

    // 시작 페이지 번호
    private int start;

    // 끝 페이지 번호
    private int end;

    // 이전 페이지 존재 여부
    private boolean prev;

    // 다음 페이지 존재 여부
    private boolean next;

    private List<E> boardList;

    /**
     * 화면에 DTO 목록과 시작 페이지 / 끝 페이지 등에 대한 처리를 담당
     * @param pageBoardRequestDto
     * @param boardList
     * @param total
     */
    @Builder(builderMethodName = "withAll")
    public PageBoardResponseDto(PageBoardRequestDto pageBoardRequestDto, List<E> boardList, int total){
        if(total <= 0){
            return;
        }
        this.page = pageBoardRequestDto.getPage();
        this.size = pageBoardRequestDto.getSize();

        this.total = total;
        this.boardList = boardList;

        this.end = (int)(Math.ceil(this.page / 10.0)) * 10; // 화면에서의 마지막 번호

        this.start = this.end - 9; // 화면에서의 시작 번호

        int last = (int)(Math.ceil((total / (double)size)));

        this.end = end > last ? last : end;

        this.prev = this.start > 1;

        this.next = total > this.end * this.size;
    }
}
