package com.keith.SportsStats.services.team_services.impl;

import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.repositories.TeamsRepository;
import com.keith.SportsStats.services.team_services.TeamsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeamsServiceImpl implements TeamsService {

    TeamsRepository teamsRepository;

    public TeamsServiceImpl(TeamsRepository teamsRepository){
        this.teamsRepository = teamsRepository;
    }

    @Override
    public TeamsEntity save(TeamsEntity teamsEntity) {
        return teamsRepository.save(teamsEntity);
    }

    @Override
    public Optional<TeamsEntity> getById(String shortName) {
        return teamsRepository.findById(shortName);
    }

    @Override
    public boolean existById(String shortName) {
        return teamsRepository.existsById(shortName);
    }

    @Override
    public void delete(String shortName) {
        teamsRepository.deleteById(shortName);
    }

    @Override
    public List<TeamsEntity> findAll() {
        return StreamSupport.stream(teamsRepository
                .findAll()
                .spliterator()
                , false)
                .collect(Collectors.toList());
    }
}
