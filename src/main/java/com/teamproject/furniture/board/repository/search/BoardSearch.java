package com.teamproject.furniture.board.repository.search;

import com.teamproject.furniture.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<Board> searchMyPost(String userId, Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

}
