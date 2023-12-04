package com.keith.SportsStats.services.game_services.impl;

import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.repositories.GamesRepository;
import com.keith.SportsStats.services.game_services.GamesService;
import com.keith.SportsStats.services.team_services.TeamsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GamesServiceImpl implements GamesService {
    private GamesRepository gamesRepository;

    private TeamsService teamsService;

    public GamesServiceImpl(GamesRepository gamesRepository, TeamsService teamsService){
        this.gamesRepository = gamesRepository;
        this.teamsService = teamsService;
    }

    @Override
    public GamesEntity save(GamesEntity gamesEntity, String homeTeamShortName, String awayTeamShortName) {
        Optional<TeamsEntity> homeTeam = teamsService.getById(homeTeamShortName);
        Optional<TeamsEntity> awayTeam = teamsService.getById(awayTeamShortName);

        if(homeTeam.isPresent() && awayTeam.isPresent()) {
            gamesEntity.setHomeTeam(homeTeam.get());
            gamesEntity.setAwayTeam(awayTeam.get());
        }

        return gamesRepository.save(gamesEntity);
    }

    @Override
    public boolean teamsExist(String homeTeamShortName, String awayTeamShortName) {
        if(teamsService.existById(homeTeamShortName) && teamsService.existById(awayTeamShortName)){
            return true;
        }
        return false;
    }
}
