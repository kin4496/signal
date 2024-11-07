package com.ssafy.signal.board.controller;

import com.ssafy.signal.board.domain.BoardDto;
import com.ssafy.signal.board.domain.BoardLikeDto;
import com.ssafy.signal.board.domain.Top10Boards;
import com.ssafy.signal.board.repository.BoardRepository;
import com.ssafy.signal.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/boards")
    public BoardDto createBoard(@RequestBody BoardDto board) {
        return boardService.createBoard(board);
    }

    @GetMapping("/boards/{id}")
    public BoardDto getBoard(@PathVariable int id) {
        return boardService.getBoard(id);
    }

    @GetMapping("/boards/all/page")
    public List<BoardDto> getAllBoards(@RequestParam int page, @RequestParam int size) {
        return boardService.getAllBoards(page,size);
    }

    @GetMapping("/boards/all/pk")
    public List<BoardDto> getAllBoardsPk(@RequestParam long boardId) {
        return boardService.getAllBoardsWithPk(boardId);
    }

    @PostMapping("/boards/like/toggle")
    public void toggleBoardLike(@RequestBody BoardLikeDto boardLikeDto) {
        boardService.toggleLike(boardLikeDto);
    }

    @GetMapping("boards/{id}/like")
    public long getBoardLike(@PathVariable long id) {
        return boardService.getBoardLike(id);
    }

    @GetMapping("boards/top")
    public Top10Boards getTopBoards() {
        return boardService.getTop10BoardList();
    }
}
