package com.example.board2.repository;

import com.example.board2.entity.Board;
import com.example.board2.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    @Query("select b, w from Board b left join b.writer w where b.bno=:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select n, w "
            + "from Board n left join n.writer w "
            + "where n.bno=:bno group by n, w"
    )
    Object getNoticeByBno(@Param("bno")Long bno);

    @Query("select n, w "
            + "from Board n left join n.writer w")
    Page<Object[]> getNotice(Pageable pageable);
}
