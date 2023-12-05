package com.keith.SportsStats.mappers.impl;

import com.keith.SportsStats.domains.dto.PlayerStatsByGameDto;
import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatsMapper implements Mapper<PlayerStatsByGameEntity, PlayerStatsByGameDto> {

    private ModelMapper modelMapper;

    public PlayerStatsMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public PlayerStatsByGameDto mapTo(PlayerStatsByGameEntity playerStatsByGameEntity) {
        return modelMapper.map(playerStatsByGameEntity, PlayerStatsByGameDto.class);
    }

    @Override
    public PlayerStatsByGameEntity mapFrom(PlayerStatsByGameDto playerStatsByGameDto) {
        return modelMapper.map(playerStatsByGameDto, PlayerStatsByGameEntity.class);
    }
}
