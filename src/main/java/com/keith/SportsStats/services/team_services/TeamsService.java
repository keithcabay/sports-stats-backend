package com.keith.SportsStats.services.team_services;

import com.keith.SportsStats.domains.dto.TeamsDto;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TeamsService {
    List<TeamsEntity> findAll();

    TeamsEntity save(TeamsEntity teamsEntity);

    Optional<TeamsEntity> getById(String shortName);

    boolean existById(String shortName);

    TeamsEntity partialUpdate(String shortName, TeamsEntity teamsEntity);

    void delete(String shortName);
}
