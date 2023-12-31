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
@Table(name = "players")
public class PlayersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long player_id;

    String first_name;

    String last_name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_team_short_name", referencedColumnName = "shortName")
    TeamsEntity team;
}
