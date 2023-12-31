package com.keith.SportsStats.services.player_services.impl;

import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.repositories.PlayersRepository;
import com.keith.SportsStats.repositories.TeamsRepository;
import com.keith.SportsStats.services.player_services.PlayersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlayersServiceImpl implements PlayersService {

    private PlayersRepository playersRepository;
    private TeamsRepository teamsRepository;

    public PlayersServiceImpl(PlayersRepository playersRepository, TeamsRepository teamsRepository){
        this.playersRepository = playersRepository;
        this.teamsRepository = teamsRepository;
    }

    @Override
    public PlayersEntity save(PlayersEntity playersEntity) {
        if(playersEntity.getTeam() != null){
            Optional<TeamsEntity> team = teamsRepository.findById(playersEntity.getTeam().getShortName());

            if (team.isPresent()) {
                playersEntity.setTeam(team.get());
            } else {
                playersEntity.setTeam(null);
            }
        }
        return playersRepository.save(playersEntity);
    }

    @Override
    public List<PlayersEntity> findAll() {
        return StreamSupport.stream(
                playersRepository.findAll().spliterator()
                , false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return playersRepository.existsById(id);
    }

    @Override
    public Optional<PlayersEntity> findByid(Long id) {
        return playersRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        playersRepository.deleteById(id);
    }

    @Override
    public PlayersEntity partialUpdate(Long id, PlayersEntity playersEntity) {
        playersEntity.setPlayer_id(id);

        return playersRepository.findById(id).map(existingPlayer -> {
            Optional.ofNullable(playersEntity.getFirst_name()).ifPresent(existingPlayer::setFirst_name);
            Optional.ofNullable(playersEntity.getLast_name()).ifPresent(existingPlayer::setLast_name);

            if(existingPlayer.getTeam() != playersEntity.getTeam() && playersEntity.getTeam() != null){
                Optional<TeamsEntity> team = teamsRepository.findById(playersEntity.getTeam().getShortName());
                team.ifPresent(existingPlayer::setTeam);
            }

            return playersRepository.save(existingPlayer);
        }).orElseThrow(() -> new RuntimeException("Player Does Not Exist"));
    }

    public List<PlayersEntity> findPlayersOnTeam(String shortName){
        return playersRepository.playersOnTeam(shortName);
    }
}
