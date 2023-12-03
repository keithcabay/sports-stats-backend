package com.keith.SportsStats.mappers.impl;

import com.keith.SportsStats.domains.dto.TeamsDto;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class TeamMapperImpl implements Mapper<TeamsEntity, TeamsDto> {

    private ModelMapper modelMapper;

    public TeamMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public TeamsDto mapTo(TeamsEntity teamsEntity) {
        return modelMapper.map(teamsEntity, TeamsDto.class);
    }

    @Override
    public TeamsEntity mapFrom(TeamsDto teamsDto) {
        return modelMapper.map(teamsDto, TeamsEntity.class);
    }
}
