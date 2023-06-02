package com.teamproject.furniture.board.repository;

import com.teamproject.furniture.board.domain.Board;
import com.teamproject.furniture.board.dtos.BoardDto;
import com.teamproject.furniture.board.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    @Query("select new com.teamproject.furniture.board.dtos.BoardDto(b.bno, b.title, b.content, b.writer, b.regDate, b.modDate) from Board b where b.writer = :writer")
    List<BoardDto> findMyBoardList(String writer);

}
