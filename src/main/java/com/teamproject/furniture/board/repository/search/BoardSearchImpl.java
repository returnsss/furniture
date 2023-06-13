package com.teamproject.furniture.board.repository.search;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.teamproject.furniture.board.domain.Board;
import com.teamproject.furniture.board.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{


    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> searchMyPost(String userId, Pageable pageable) { //todo 추후 삭제 예정. 필요없는 코드임
        QBoard board = QBoard.board; // Q도메인 객체
        JPQLQuery<Board> query = from(board); // select ... from board
        query.where(board.writer.eq(userId)); // where title like

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board); // select ... from board

        if( (types != null && types.length > 0) && keyword != null){ // 검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type: types){
                switch (type){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            } // end for
            query.where(booleanBuilder);
        } // end if



        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
