package com.keith.SportsStats.mappers.impl;

import com.keith.SportsStats.domains.dto.PlayersDto;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapperImpl implements Mapper<PlayersEntity, PlayersDto> {

    private ModelMapper modelMapper;

    public PlayerMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public PlayersDto mapTo(PlayersEntity playersEntity) {
        return modelMapper.map(playersEntity, PlayersDto.class);
    }

    @Override
    public PlayersEntity mapFrom(PlayersDto playersDto) {
        return modelMapper.map(playersDto, PlayersEntity.class);
    }
}
