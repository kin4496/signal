package com.ssafy.signal.match.domain;

import com.ssafy.signal.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MatchDto {
    private Long matchId;

    private Long userId;

    private Double latitude;
    private Double longtitude;

    public MatchEntity asEntity(UserEntity user){
        return MatchEntity
                .builder()
                .user(user)
                .latitude(latitude)
                .longtitude(longtitude)
                .build();
    }
}
