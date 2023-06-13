package com.teamproject.furniture.board.repository;

import com.teamproject.furniture.board.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void testInsert(){
        for(int i = 1; i <= 100; i++){
            Board board = Board.builder()
                    .title("title...")
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        }
    }

    @Test
    public void testSelect(){
        Long bno = 100L;
        /*Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();*/
        Board board = boardRepository.findById(bno).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물"));
        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;
        Board board = boardRepository.findById(bno).orElseThrow();

        board.change("update... title 100", "update content 100");
        boardRepository.save(board);
    }

    @Test
    public void testUpdate2(){ // 어떤 bno를 바꿔야 되는지 찾을수 없음
        Long bno = 100L;
        Board board = Board.builder()
                .bno(bno)
                .title("title...")
                .content("content...update2")
                .build();

        boardRepository.save(board);
    }

    @Test
    public void testUpdate3(){ // 없는 bno 지정한 경우
        Long bno = 1000L;
        Board board = Board.builder()
                .bno(bno)
                .title("title...")
                .content("content...update3")
                .writer("user...update")
                .build();

        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
        // 1 page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total page : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());
        // prev next
        log.info(result.hasPrevious() + " : " + result.hasNext());

        List<Board> boardList = result.getContent();

        boardList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        // 2 page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        String userId = "test01";
        boardRepository.searchMyPost(userId, pageable);
    }

    @Test
    public void testSearchAll(){
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchAll2(){
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // total pages
        log.info(result.getTotalPages());

        // page size
        log.info(result.getSize());

        // pageNumber
        log.info(result.getNumber());

        // prev next
        log.info(result.hasPrevious() + " : " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }
}
