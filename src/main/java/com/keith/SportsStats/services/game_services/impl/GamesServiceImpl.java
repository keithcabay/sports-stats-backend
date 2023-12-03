package com.keith.SportsStats.services.game_services.impl;

import com.keith.SportsStats.repositories.GamesRepository;
import com.keith.SportsStats.services.game_services.GamesService;
import org.springframework.stereotype.Service;

@Service
public class GamesServiceImpl implements GamesService {
    private GamesRepository gamesRepository;

    public GamesServiceImpl(GamesRepository gamesRepository){
        this.gamesRepository = gamesRepository;
    }
}
