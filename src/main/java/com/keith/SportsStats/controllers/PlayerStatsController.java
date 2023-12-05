package com.keith.SportsStats.controllers;

import com.keith.SportsStats.domains.dto.PlayerStatsByGameDto;
import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.mappers.impl.PlayerStatsMapper;
import com.keith.SportsStats.services.player_stats_services.PlayerStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlayerStatsController {

    PlayerStatsService playerStatsService;

    PlayerStatsMapper playerStatsMapper;

    public PlayerStatsController(PlayerStatsService playerStatsService, PlayerStatsMapper playerStatsMapper){
        this.playerStatsService = playerStatsService;
        this.playerStatsMapper = playerStatsMapper;
    }

    @PostMapping(path = "/player-stats/{player_id}/{game_id}")
    public ResponseEntity<PlayerStatsByGameDto> addNewPlayerStat
            (@PathVariable("player_id") Long player_id
            , @PathVariable("game_id") Long game_id
            , @RequestBody PlayerStatsByGameDto playerStatsDto){

        if(!playerStatsService.playerAndGameExist(player_id, game_id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PlayerStatsByGameEntity playerStatsEntity = playerStatsMapper.mapFrom(playerStatsDto);
        PlayerStatsByGameEntity returnedPlayerStatsEntity = playerStatsService.save(playerStatsEntity, player_id, game_id);
        PlayerStatsByGameDto returnedPlayerStatsDto = playerStatsMapper.mapTo(returnedPlayerStatsEntity);

        return new ResponseEntity<>(returnedPlayerStatsDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/player-stats/{player_id}")
    public List<PlayerStatsByGameDto> getAllStatsByPlayerId(@PathVariable("player_id") Long player_id){
        List<PlayerStatsByGameEntity> allPlayerStats = playerStatsService.getAllPlayerStats(player_id);

        return allPlayerStats.stream()
                .map(stat -> playerStatsMapper.mapTo(stat)
                ).collect(Collectors.toList());
    }
}
