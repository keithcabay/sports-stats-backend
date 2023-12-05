package com.keith.SportsStats.repositories;

import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.domains.entity.PlayerStatsId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatsRepository extends CrudRepository<PlayerStatsByGameEntity, PlayerStatsId> {
}
