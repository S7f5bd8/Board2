package com.example.board2.repository.search;

import com.example.board2.entity.Board;
import com.example.board2.entity.QBoard;
import com.example.board2.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1(){
        log.info("search1 메소드 호출됨");
        QBoard board = QBoard.board;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email);
        tuple.groupBy(board, member);

        log.info("=========================");
        log.info(jpqlQuery);
        log.info("=========================");

        //jpql 실행 방법
        List<Tuple> result = tuple.fetch();

        log.info("=========================");
        log.info(result);
        log.info("=========================");

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage() 메소드 호출됨");

        QBoard board = QBoard.board;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email);
        tuple.groupBy(board, member);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        booleanBuilder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;

                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;

                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                } // end switch
            } // end for
            booleanBuilder.and(conditionBuilder);
        } // end if

        tuple.where(booleanBuilder);
        tuple.groupBy(board, member);

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }
}