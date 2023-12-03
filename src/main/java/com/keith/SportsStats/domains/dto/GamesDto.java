package com.keith.SportsStats.domains.dto;

import com.keith.SportsStats.domains.entity.TeamsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamesDto {
    Long id;

    LocalDate date;

    Time time;

    TeamsDto homeTeam;

    TeamsDto awayTeam;

    int homeTeamScore;

    int awayTeamScore;
}
