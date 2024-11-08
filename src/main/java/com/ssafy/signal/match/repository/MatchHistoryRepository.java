package com.ssafy.signal.match.repository;

import com.ssafy.signal.match.domain.MatchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchHistoryRepository extends JpaRepository<MatchHistoryEntity, Long> {
}
