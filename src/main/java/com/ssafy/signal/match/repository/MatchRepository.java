package com.ssafy.signal.match.repository;

import com.ssafy.signal.match.domain.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
    Optional<MatchEntity> findByUserUserId(Long userId);
}
