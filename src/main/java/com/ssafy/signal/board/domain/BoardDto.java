package com.ssafy.signal.board.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class BoardDto {
    private Long boardId;

    private String title;
    private String content;

    public BoardEntity asEntity(){
        return BoardEntity.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .build();
    }
}
