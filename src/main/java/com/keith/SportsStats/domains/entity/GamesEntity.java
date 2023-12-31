package com.keith.SportsStats.domains.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "games")
public class GamesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long game_id;

    @Column(columnDefinition = "DATE")
    LocalDate date;

    @Column(columnDefinition = "TIME")
    LocalTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_team_short_name", referencedColumnName = "shortName")
    TeamsEntity homeTeam;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "away_team_short_name", referencedColumnName = "shortName")
    TeamsEntity awayTeam;

    int homeTeamScore;

    int awayTeamScore;
}
