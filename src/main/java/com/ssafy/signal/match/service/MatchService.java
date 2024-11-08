package com.ssafy.signal.match.service;

import com.ssafy.signal.match.domain.*;
import com.ssafy.signal.match.repository.MatchHistoryRepository;
import com.ssafy.signal.match.repository.MatchRepository;
import com.ssafy.signal.user.domain.UserEntity;
import com.ssafy.signal.user.repository.UserRepository;
import com.ssafy.signal.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final MatchHistoryRepository matchHistoryRepository;
    private final StringRedisTemplate redisTemplate;

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

    public void insertTestInfoRedis()
    {
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();


        val list = matchRepository.findAll();
        String key = "geoPoints";


        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) redisTemplate.delete(key);

        for (MatchEntity matchEntity : list) {
            val pos = matchEntity.asDto();
            Point point = new Point(pos.getLongtitude(), pos.getLatitude());

            // redis에 geohash로 인코딩한 값 저장
            geoOperations.add(key, point, pos.getUserId() + "");
        }
    }

    public List<NearUser> getNearUserWithRedis(Double latitude, Double longitude) {
        // Geo에 저장된 사용자 이름으로 GeoReference 객체 생성
        GeoReference reference = GeoReference.fromMember("1");

        // 사용자의 위치를 중심으로 반경 100m 범위 설정
        Distance radius = new Distance(10, RedisGeoCommands.DistanceUnit.KILOMETERS);

        // redis geo 명령어 옵션 설정, limit 13
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending();

        // redis geo 자료구조 설정
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();

        // 설정한 위치 범위 안에 있는 사용자들 검색
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations
                .search("geoPoints", reference, radius, args);

        List<NearUser> list = new ArrayList<>();

        // 범위 안에 있는 사용자에 대해 최근 재생 곡 조회
        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            RedisGeoCommands.GeoLocation<String> location = result.getContent();

            String nearbyUserName = location.getName();

            // 중심 좌표로부터의 거리를 기준으로 level 지정
            double distance = result.getDistance().getValue();

            list.add(NearUser.builder()
                    .userId(Long.parseLong(nearbyUserName))
                    .build());
        }
        return list;
    }
}
