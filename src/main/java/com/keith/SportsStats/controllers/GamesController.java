package com.keith.SportsStats.controllers;

import com.keith.SportsStats.domains.dto.GamesDto;
import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.mappers.Mapper;
import com.keith.SportsStats.mappers.impl.GamesMapperImpl;
import com.keith.SportsStats.services.game_services.GamesService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamesController {

    private GamesService gamesService;
    private Mapper<GamesEntity, GamesDto> gameMapper;

    public GamesController(GamesService gamesService, Mapper<GamesEntity, GamesDto> gameMapper){
        this.gamesService = gamesService;
        this.gameMapper = gameMapper;
    }
}
