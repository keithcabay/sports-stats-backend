package com.keith.SportsStats.controllers;

import com.keith.SportsStats.domains.dto.TeamsDto;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.mappers.Mapper;
import com.keith.SportsStats.services.team_services.TeamsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TeamsController {

    private TeamsService teamsService;
    private Mapper<TeamsEntity, TeamsDto> teamsMapper;

    public TeamsController(TeamsService teamsService, Mapper<TeamsEntity, TeamsDto> teamsMapper){
        this.teamsMapper = teamsMapper;
        this.teamsService = teamsService;
    }

    @PostMapping(path = "/teams")
    public ResponseEntity<TeamsDto> createTeam(@RequestBody TeamsDto teamsDto){
        TeamsEntity teamsEntity = teamsMapper.mapFrom(teamsDto);
        TeamsEntity returnedEntity = teamsService.save(teamsEntity);
        TeamsDto returnedDto = teamsMapper.mapTo(returnedEntity);

        return new ResponseEntity<>(returnedDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/teams")
    public List<TeamsDto> listTeams(){
        List<TeamsEntity> teamsList = teamsService.findAll();
        return teamsList.stream().map(teamsMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/teams/{shortName}")
    public ResponseEntity<TeamsDto> getTeamByName(@PathVariable("shortName") String shortName){
        Optional<TeamsEntity> receivedTeam = teamsService.getById(shortName);

        return receivedTeam.map(TeamsEntity -> {
            TeamsDto teamsDto = teamsMapper.mapTo(TeamsEntity);
            return new ResponseEntity<>(teamsDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping(path = "/teams/{shortName}")
    public ResponseEntity<TeamsDto> fullUpdateTeam(@PathVariable("shortName") String shortName, @RequestBody TeamsDto teamsDto){
        if(!teamsService.existById(shortName)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        teamsDto.setShortName(shortName);

        TeamsEntity convertedTeamFromDto = teamsMapper.mapFrom(teamsDto);
        TeamsEntity returnedTeamEntity = teamsService.save(convertedTeamFromDto);
        TeamsDto convertedTeamFromEntity = teamsMapper.mapTo(returnedTeamEntity);

        return new ResponseEntity<>(convertedTeamFromEntity, HttpStatus.OK);
    }

    @PatchMapping(path = "/teams/{shortName}")
    public ResponseEntity<TeamsDto> partialUpdateTeam(@PathVariable("shortName") String shortName, @RequestBody TeamsDto teamsDto){
        if(!teamsService.existById(shortName)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TeamsEntity convertedTeamFromDto = teamsMapper.mapFrom(teamsDto);
        TeamsEntity returnedTeamEntity = teamsService.partialUpdate(shortName, convertedTeamFromDto);
        TeamsDto convertedTeamFromEntity = teamsMapper.mapTo(returnedTeamEntity);

        return new ResponseEntity<>(convertedTeamFromEntity, HttpStatus.OK);
    }

    @DeleteMapping(path = "/teams/{shortName}")
    public ResponseEntity deleteTeam(@PathVariable("shortName") String shortName){
        teamsService.delete(shortName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
