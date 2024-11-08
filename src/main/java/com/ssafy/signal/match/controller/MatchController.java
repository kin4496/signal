package com.ssafy.signal.match.controller;

import com.ssafy.signal.match.domain.MatchDto;
import com.ssafy.signal.match.domain.NearUser;
import com.ssafy.signal.match.service.MatchService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MatchController {
    private final MatchService matchService;

    @PostMapping("/match")
    public MatchDto registMatch(@RequestBody MatchDto match) {
        return matchService.registMatch(match);
    }

    @GetMapping("/match/near")
    public List<NearUser> nearMatch(@RequestParam Double lat, @RequestParam Double lon) {
        return matchService.getNearUser(lat,lon);
    }
}
