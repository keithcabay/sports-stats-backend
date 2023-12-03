package com.keith.SportsStats.services.player_services;

import com.keith.SportsStats.domains.dto.PlayersDto;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PlayersService {
    PlayersEntity save(PlayersEntity playersEntity);

    List<PlayersEntity> findAll();

    boolean existsById(Long id);

    Optional<PlayersEntity> findByid(Long id);

    void deleteById(Long id);

    PlayersEntity partialUpdate(Long id, PlayersEntity playersEntity);

    List<PlayersEntity> findPlayersOnTeam(String shortName);
}
