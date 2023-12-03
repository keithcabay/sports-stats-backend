package com.keith.SportsStats.repositories;

import com.keith.SportsStats.domains.entity.TeamsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends CrudRepository<TeamsEntity, String> {
}
