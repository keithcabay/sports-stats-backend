package com.keith.SportsStats.services.player_stats_services.impl;

import com.keith.SportsStats.repositories.PlayerStatsRepository;
import com.keith.SportsStats.services.player_stats_services.PlayerStatsService;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsServiceImpl implements PlayerStatsService {

    PlayerStatsRepository playerStatsRepository;

    private PlayerStatsServiceImpl(PlayerStatsRepository playerStatsRepository){
        this.playerStatsRepository = playerStatsRepository;
    }
}
