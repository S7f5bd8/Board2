package com.example.board2.repository.search;


import com.example.board2.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Board search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
