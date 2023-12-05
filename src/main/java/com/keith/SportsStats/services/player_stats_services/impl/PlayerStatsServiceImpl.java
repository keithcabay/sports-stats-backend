package com.keith.SportsStats.services.player_stats_services.impl;

import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.repositories.PlayerStatsRepository;
import com.keith.SportsStats.services.game_services.GamesService;
import com.keith.SportsStats.services.player_services.PlayersService;
import com.keith.SportsStats.services.player_stats_services.PlayerStatsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerStatsServiceImpl implements PlayerStatsService {

    PlayerStatsRepository playerStatsRepository;

    PlayersService playersService;

    GamesService gamesService;

    private PlayerStatsServiceImpl(PlayerStatsRepository playerStatsRepository, PlayersService playersService, GamesService gamesService){
        this.playerStatsRepository = playerStatsRepository;
        this.playersService = playersService;
        this.gamesService = gamesService;
    }

    @Override
    public boolean playerAndGameExist(Long playerId, Long gameId) {
        if(!playersService.existsById(playerId) || !gamesService.existById(gameId)){
            return false;
        }
        return true;
    }

    @Override
    public PlayerStatsByGameEntity save(PlayerStatsByGameEntity playerStatsEntity, Long playerId, Long gameId) {
        Optional<PlayersEntity> player = playersService.findByid(playerId);
        Optional<GamesEntity> game = gamesService.findById(gameId);

        //add logic for the given player needing to be on the given team

        if(player.isPresent() && game.isPresent()){
            playerStatsEntity.setPlayer(player.get());
            playerStatsEntity.setGame(game.get());
        }

        return playerStatsRepository.save(playerStatsEntity);
    }
}
