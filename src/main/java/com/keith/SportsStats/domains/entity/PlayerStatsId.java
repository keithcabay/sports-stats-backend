package com.keith.SportsStats.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerStatsId implements Serializable {
    private PlayersEntity playersEntity;
    private GamesEntity gamesEntity;
}
