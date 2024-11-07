package com.ssafy.signal.board.domain;

import com.ssafy.signal.user.domain.UserEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardLikeDto {

    private Long id;

    private Long boardId;
    private Long userId;

    public BoardLikeEntity asEntity(
            BoardEntity board,
            UserEntity user){
        return BoardLikeEntity.builder()
                .board(board)
                .user(user)
                .build();
    }
}
