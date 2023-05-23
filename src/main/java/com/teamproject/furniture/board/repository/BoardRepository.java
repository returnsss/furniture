package com.teamproject.furniture.board.repository;

import com.teamproject.furniture.board.domain.Board;
import com.teamproject.furniture.board.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    @Query(value = "select now()", nativeQuery = true)
    String getTime();
}
