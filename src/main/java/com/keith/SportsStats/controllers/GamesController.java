package com.keith.SportsStats.controllers;

import com.keith.SportsStats.domains.dto.GamesDto;
import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.mappers.Mapper;
import com.keith.SportsStats.services.game_services.GamesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/games/{id}")
    public ResponseEntity<GamesDto> getGameById(@PathVariable("id") Long id){

        Optional<GamesEntity> foundGameEntity = gamesService.findById(id);

        return foundGameEntity.map(game -> {
            GamesDto gamesDto = gameMapper.mapTo(game);
            return new ResponseEntity<>(gamesDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }


    //patch used in the event of a postponed game or to update the scores
    @PatchMapping(path = "/games/{id}")
    public ResponseEntity<GamesDto> partialUpdateGame(@PathVariable("id") Long id, @RequestBody GamesDto gamesDto){
        if(!gamesService.existById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GamesEntity convertedEntityFromDto = gameMapper.mapFrom(gamesDto);
        GamesEntity returnedGame = gamesService.partialUpdate(id, convertedEntityFromDto);
        GamesDto returnedGameDto = gameMapper.mapTo(returnedGame);

        return new ResponseEntity<>(returnedGameDto, HttpStatus.OK);
    }
}
