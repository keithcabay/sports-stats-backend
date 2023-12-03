package com.keith.SportsStats.mappers.impl;

import com.keith.SportsStats.domains.dto.GamesDto;
import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GamesMapperImpl implements Mapper<GamesEntity, GamesDto> {

    private ModelMapper modelMapper;

    public GamesMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public GamesDto mapTo(GamesEntity gamesEntity) {
        return modelMapper.map(gamesEntity, GamesDto.class);
    }

    @Override
    public GamesEntity mapFrom(GamesDto gamesDto) {
        return modelMapper.map(gamesDto, GamesEntity.class);
    }
}
