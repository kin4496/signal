package com.ssafy.signal.match.domain;

import com.ssafy.signal.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_match")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private Double latitude;

    @Column
    private Double longtitude;

    public MatchDto asDto(){
        return MatchDto
                .builder()
                .matchId(this.matchId)
                .latitude(latitude)
                .longtitude(longtitude)
                .userId(user.getUserId())
                .build();
    }
}
