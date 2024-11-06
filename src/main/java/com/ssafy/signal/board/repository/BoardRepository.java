package com.ssafy.signal.board.repository;

import com.ssafy.signal.board.domain.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query("select b from BoardEntity b where b.boardId > :id")
    List<BoardEntity> findAllWithPK(@Param("id") long boardId);
}
