package com.keith.SportsStats.domains.dto;


import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayersDto {
    Long player_id;

    String first_name;
    String last_name;

    TeamsDto team;

    List<PlayerStatsByGameDto> statsByGame;
}
