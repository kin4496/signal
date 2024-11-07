package com.ssafy.signal.board.repository;

import com.ssafy.signal.board.domain.BoardEntity;
import com.ssafy.signal.board.domain.BoardLikeEntity;
import com.ssafy.signal.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long> {
    Optional<BoardLikeEntity> findByUserAndBoard(UserEntity user,BoardEntity board);
    long countByBoard(BoardEntity board);

    @Query("SELECT b FROM BoardEntity b " +
            "LEFT JOIN BoardLikeEntity bl ON b.boardId = bl.board.boardId " +
            "GROUP BY b.boardId " +
            "ORDER BY COUNT(bl.id) DESC " +
            "LIMIT 10")
    List<BoardEntity> findTop10BoardsByLikes();
}
