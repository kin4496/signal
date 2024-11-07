package com.ssafy.signal.board.domain;

import com.ssafy.signal.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "board_like")
public class BoardLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public BoardLikeDto asDto(){
        return BoardLikeDto
                .builder()
                .id(id)
                .boardId(board.getBoardId())
                .userId(user.getUserId())
                .build();
    }
}
