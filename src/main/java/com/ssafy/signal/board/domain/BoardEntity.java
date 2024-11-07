package com.ssafy.signal.board.domain;

import com.ssafy.signal.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "Board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    @Column
    private String title;

    @Column
    private String content;

    public BoardDto asDto(){
        return BoardDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .build();
    }
}
