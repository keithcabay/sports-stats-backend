package com.keith.SportsStats.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "player_stats_by_game")
public class PlayerStatsByGameEntity {

    @Id
    @ManyToOne()
    @JoinColumn(name = "game_id")
    GamesEntity game;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    PlayersEntity player;

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