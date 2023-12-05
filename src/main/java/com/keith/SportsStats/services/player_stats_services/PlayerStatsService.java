package com.keith.SportsStats.services.player_stats_services;

import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import org.springframework.stereotype.Service;

@Service
public interface PlayerStatsService {
    boolean playerAndGameExist(Long playerId, Long gameId);

    PlayerStatsByGameEntity save(PlayerStatsByGameEntity playerStatsEntity, Long playerId, Long gameId);
}
