package com.keith.SportsStats.domains.dto;

import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerStatsByGameDto {
    GamesDto game;

    PlayersDto player;

    float minutes_played;

    float points;

    float offensive_rebounds;

    float defensive_rebounds;

    float assists;

    float steals;

    float blocks;

    float turnovers;

    float fouls;

    float three_pointers_made;

    float three_pointers_attempted;

    float free_throws_made;

    float free_throws_attempted;

    float two_pointers_made;

    float two_pointers_attempted;
}
