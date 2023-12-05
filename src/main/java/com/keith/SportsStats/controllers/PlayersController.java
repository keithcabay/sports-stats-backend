package com.keith.SportsStats.controllers;

import com.keith.SportsStats.domains.dto.PlayersDto;
import com.keith.SportsStats.domains.dto.TeamsDto;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.mappers.Mapper;
import com.keith.SportsStats.services.player_services.PlayersService;
import com.keith.SportsStats.services.player_services.impl.PlayersServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PlayersController {

    private PlayersService playersService;
    private Mapper<PlayersEntity, PlayersDto> playersMapper;

    public PlayersController(PlayersService playersService, Mapper<PlayersEntity, PlayersDto> playersMapper, Mapper<TeamsEntity, TeamsDto> teamsMapper, PlayersServiceImpl playersServiceImpl){
        this.playersService = playersService;
        this.playersMapper = playersMapper;
    }

    @PostMapping(path = "/players")
    public ResponseEntity<PlayersDto> createPlayer(@RequestBody PlayersDto playersDto){

        PlayersEntity convertedPlayerFromDto = playersMapper.mapFrom(playersDto);

        PlayersEntity returnedPlayerEntity = playersService.save(convertedPlayerFromDto);

        PlayersDto convertedReturnedEntityToDto = playersMapper.mapTo(returnedPlayerEntity);
        return new ResponseEntity<>(convertedReturnedEntityToDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/players/teams/{shortName}")
    public ResponseEntity<List<PlayersDto>>  playerByTeamShortName(@PathVariable("shortName") String shortName){
        List<PlayersEntity> playerListOnTeam = playersService.findPlayersOnTeam(shortName);
        if(playerListOnTeam.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PlayersDto> playersDto = playerListOnTeam.stream().map(playersMapper::mapTo).toList();

        return new ResponseEntity<>(playersDto, HttpStatus.OK);
    }

    @GetMapping(path = "/players")
    public List<PlayersDto> listAllPlayers(){
        List<PlayersEntity> playerList = playersService.findAll();

        return playerList.stream().map(playersEntity -> playersMapper.mapTo(playersEntity)).collect(Collectors.toList());
    }

    @GetMapping(path = "/players/{player_id}")
    public ResponseEntity<PlayersDto> getPlayerByTeam(@PathVariable("player_id") Long id){

        Optional<PlayersEntity> returnedPlayerEntity = playersService.findByid(id);
        return returnedPlayerEntity.map(playersEntity -> {
            PlayersDto convertedPlayerDtoFromEntity = playersMapper.mapTo(playersEntity);
            return new ResponseEntity<>(convertedPlayerDtoFromEntity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/players/{player_id}")
    public ResponseEntity<PlayersDto> partialUpdatePlayer(@PathVariable("player_id") Long id ,@RequestBody PlayersDto playersDto){
        if(!playersService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PlayersEntity convertedFromPlayerDto = playersMapper.mapFrom(playersDto);
        PlayersEntity returnedPlayerEntity = playersService.partialUpdate(id, convertedFromPlayerDto);
        PlayersDto convertedReturnedPlayerEntity = playersMapper.mapTo(returnedPlayerEntity);

        return new ResponseEntity<>(convertedReturnedPlayerEntity, HttpStatus.OK);
    }


    @DeleteMapping(path = "/players/{player_id}")
    public ResponseEntity deletePlayerById(@PathVariable("player_id") Long id){
        playersService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
