package com.keith.SportsStats.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayersDto {
    Long player_id;

    String first_name;
    String last_name;

    TeamsDto team;
}
