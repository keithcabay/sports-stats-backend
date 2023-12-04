package com.keith.SportsStats.services.game_services;

import com.keith.SportsStats.domains.dto.GamesDto;
import com.keith.SportsStats.domains.entity.GamesEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GamesService {
    GamesEntity save(GamesEntity gamesEntity, String homeTeamShortName, String awayTeamShortName);

    boolean teamsExist(String homeTeamShortName, String awayTeamShortName);

    boolean existById(Long id);

    Optional<GamesEntity> findById(Long id);
}
