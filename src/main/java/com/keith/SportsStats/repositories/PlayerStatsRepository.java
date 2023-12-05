package com.keith.SportsStats.repositories;

import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.domains.entity.PlayerStatsId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository extends CrudRepository<PlayerStatsByGameEntity, PlayerStatsId> {

    @Query("SELECT ps FROM PlayerStatsByGameEntity ps WHERE ps.player.player_id = :player_id")
    List<PlayerStatsByGameEntity> allPlayerStats(Long player_id);
}
