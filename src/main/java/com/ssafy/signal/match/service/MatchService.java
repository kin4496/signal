package com.ssafy.signal.match.service;

import com.ssafy.signal.match.domain.*;
import com.ssafy.signal.match.repository.MatchHistoryRepository;
import com.ssafy.signal.match.repository.MatchRepository;
import com.ssafy.signal.user.domain.UserEntity;
import com.ssafy.signal.user.repository.UserRepository;
import com.ssafy.signal.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final MatchHistoryRepository matchHistoryRepository;

    public MatchDto registMatch(MatchDto matchDto) {
        boolean isAlreadyRegist = matchRepository
                .findByUserUserId(matchDto.getUserId())
                .isPresent();

        if(isAlreadyRegist) return matchDto;

        UserEntity user = userRepository.findById(matchDto.getUserId()).orElseThrow();
        return matchRepository
                .save(matchDto.asEntity(user))
                .asDto();
    }

    public List<NearUser> getNearUser(Double latitude, Double longitude)
    {
        return matchRepository
                .findAll()
                .stream()
                .map(MatchEntity::asDto)
                .filter(m-> GeometryUtil.isNear(latitude,longitude,m.getLatitude(),m.getLongtitude()))
                .map(m->NearUser.builder().userId(m.getUserId()).build())
                .toList();
    }

    public void requireMatch(Long to)
    {
        log.info(to + "에게 매칭을 요청했습니다.");
    }

    public MatchHistoryDto matchUser(MatchHistoryDto historyDto) {
        UserEntity from = userRepository.findById(historyDto.getFrom()).orElseThrow();
        UserEntity to = userRepository.findById(historyDto.getTo()).orElseThrow();
        return matchHistoryRepository.save(historyDto.asEntity(from,to)).asDto();
    }


}
