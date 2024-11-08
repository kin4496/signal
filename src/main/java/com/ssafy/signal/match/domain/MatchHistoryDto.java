package com.ssafy.signal.match.domain;

import com.ssafy.signal.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchHistoryDto {

    private Long historyId;

    private Long from;
    private Long to;

    public MatchHistoryEntity asEntity(UserEntity from, UserEntity to)
    {
        return MatchHistoryEntity
                .builder()
                .from(from)
                .to(to)
                .build();
    }
}
