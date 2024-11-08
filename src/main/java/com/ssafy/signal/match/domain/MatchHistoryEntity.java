package com.ssafy.signal.match.domain;

import com.ssafy.signal.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "match_history")
public class MatchHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="history_id")
    private Long historyId;

    @OneToOne
    @JoinColumn(name ="from_id")
    private UserEntity from;

    @OneToOne
    @JoinColumn(name ="to_id")
    private UserEntity to;


    public MatchHistoryDto asDto(){
        return MatchHistoryDto
                .builder()
                .historyId(historyId)
                .from(from.getUserId())
                .to(to.getUserId())
                .build();
    }
}
