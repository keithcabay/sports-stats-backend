package com.keith.SportsStats.repositories;

import com.keith.SportsStats.domains.entity.PlayersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.management.PlatformLoggingMXBean;
import java.util.List;

@Repository
public interface PlayersRepository extends CrudRepository<PlayersEntity, Long>{
    @Query("SELECT p FROM PlayersEntity p WHERE p.team.shortName = :shortName")
    List<PlayersEntity> playersOnTeam(String shortName);
}
