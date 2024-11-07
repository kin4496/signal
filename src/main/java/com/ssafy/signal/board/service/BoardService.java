package com.ssafy.signal.board.service;

import com.ssafy.signal.board.domain.BoardDto;
import com.ssafy.signal.board.domain.BoardEntity;
import com.ssafy.signal.board.domain.BoardLikeDto;
import com.ssafy.signal.board.domain.BoardLikeEntity;
import com.ssafy.signal.board.repository.BoardLikeRepository;
import com.ssafy.signal.board.repository.BoardRepository;
import com.ssafy.signal.user.domain.UserEntity;
import com.ssafy.signal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final UserRepository userRepository;

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

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

    @Transactional
    public void toggleLike(BoardLikeDto boardLikeDto) {
        BoardEntity board = boardRepository
                .findById(boardLikeDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));

        UserEntity user = userRepository
                .findById(boardLikeDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Optional<BoardLikeEntity> existingLike = boardLikeRepository
                .findByUserAndBoard(user, board);

        if(existingLike.isPresent()) boardLikeRepository.delete(existingLike.get());
        else boardLikeRepository
                .save(boardLikeDto.asEntity(board, user));
    }

    public long getBoardLike(Long id) {
        BoardEntity board = boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        return boardLikeRepository.countByBoard(board);
    }

    public List<BoardDto> getTop10BoardList(){
        return boardLikeRepository
                .findTop10BoardsByLikes()
                .stream()
                .map(BoardEntity::asDto)
                .toList();
    }
}
