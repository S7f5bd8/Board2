package com.example.board2.service;

import com.example.board2.dto.BoardDTO;
import com.example.board2.dto.PageRequestDTO;
import com.example.board2.dto.PageResultDTO;
import com.example.board2.entity.Board;
import com.example.board2.entity.Member;
import com.example.board2.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository;
    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        repository.save(board);

        return board.getBno();
    }
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1]));
        Page<Object[]> result = repository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }
    @Override
    public BoardDTO get(Long bno) {
        Object result = repository
                .getNoticeByBno(bno);

        Object[] arr = (Object[]) result;
        BoardDTO boardDTO = entityToDTO((Board) arr[0], (Member) arr[1]);

        return boardDTO;
    }
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getReferenceById(boardDTO.getBno());
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }
}