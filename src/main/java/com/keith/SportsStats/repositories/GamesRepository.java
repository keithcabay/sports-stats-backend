package com.keith.SportsStats.repositories;

import com.keith.SportsStats.domains.entity.GamesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends CrudRepository<GamesEntity, Long> {
}
