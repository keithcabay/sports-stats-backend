package com.keith.SportsStats.controllers;

import com.keith.SportsStats.domains.dto.GamesDto;
import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.mappers.Mapper;
import com.keith.SportsStats.services.game_services.GamesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamesController {

    private GamesService gamesService;
    private Mapper<GamesEntity, GamesDto> gameMapper;

    public GamesController(GamesService gamesService, Mapper<GamesEntity, GamesDto> gameMapper){
        this.gamesService = gamesService;
        this.gameMapper = gameMapper;
    }

    @PostMapping(path = "/games/{homeTeam}/{awayTeam}")
    public ResponseEntity<GamesDto> addNewGame(@PathVariable("homeTeam") String homeTeamShortName
            , @PathVariable("awayTeam") String awayTeamShortName
            , @RequestBody GamesDto gamesDto)
    {
        if (!gamesService.teamsExist(homeTeamShortName, awayTeamShortName)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GamesEntity convertedEntityFromDto = gameMapper.mapFrom(gamesDto);
        GamesEntity returnedSavedEntity = gamesService.save(convertedEntityFromDto, homeTeamShortName, awayTeamShortName);
        GamesDto finalSavedDto = gameMapper.mapTo(returnedSavedEntity);

        return new ResponseEntity<>(finalSavedDto, HttpStatus.CREATED);
    }
}
