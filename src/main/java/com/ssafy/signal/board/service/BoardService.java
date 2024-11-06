package com.ssafy.signal.board.service;

import com.ssafy.signal.board.domain.BoardDto;
import com.ssafy.signal.board.domain.BoardEntity;
import com.ssafy.signal.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDto) {
        return boardRepository
                .save(boardDto.asEntity())
                .asDto();
    }

    public BoardDto getBoard(long id){
        return boardRepository
                .findById(id)
                .map(BoardEntity::asDto)
                .orElse(null);
    }

    public List<BoardDto> getAllBoards(int page, int size){
        return boardRepository
                .findAll(PageRequest.of(page,size))
                .map(BoardEntity::asDto)
                .getContent();
    }

    public List<BoardDto> getAllBoardsWithPk(long id){
        return boardRepository
                .findAllWithPK(id)
                .stream()
                .map(BoardEntity::asDto)
                .toList();
    }
}
