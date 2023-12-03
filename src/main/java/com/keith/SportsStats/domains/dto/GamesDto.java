package com.keith.SportsStats.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamesDto {
    Long id;

    LocalDate date;

    LocalTime time;

    TeamsDto homeTeam;

    TeamsDto awayTeam;

    int homeTeamScore;

    int awayTeamScore;
}
