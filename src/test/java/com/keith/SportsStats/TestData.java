package com.keith.SportsStats;

import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestData {
    private TestData(){}

    public static TeamsEntity createKnicksTeam(){
        return TeamsEntity.builder()
                .shortName("NYK")
                .fullName("New York Knicks")
                .build();
    }

    public static TeamsEntity createBucksTeam(){
        return TeamsEntity.builder()
                .shortName("MIL")
                .fullName("Milwaukee Bucks")
                .build();
    }

    public static PlayersEntity createPlayerAonKnicks(){
        //named on knicks for easier understanding but still need to persist a created knicks team, then setTeam
        return PlayersEntity.builder()
                .player_id(1L)
                .first_name("Jalen")
                .last_name("Brunson")
                .team(null)
                .build();
    }

    public static PlayersEntity createPlayerBonBucks(){
        //named on bucks for easier understanding but still need to persist a created bucks team, then setTeam
        return PlayersEntity.builder()
                .player_id(2L)
                .first_name("Giannis")
                .last_name("Antetokounmpo")
                .team(null)
                .build();
    }

    public static PlayersEntity createPlayerNoTeam(){
        return PlayersEntity.builder()
                .player_id(3L)
                .first_name("Michael")
                .last_name("Jordan")
                .team(null)
                .build();
    }

    public static PlayersEntity createPlayerNonExistingTeam(){
        return PlayersEntity.builder()
                .player_id(4L)
                .first_name("Kobe")
                .last_name("Bryant")
                .team(createKnicksTeam())
                //note: team is never persisted to database so does not exist
                // in database but does refer to TeamsEntity knicks object including shortName
                .build();
    }

    public static GamesEntity createGameNov25(){
        return GamesEntity.builder()
                .game_id(1L)
                .date(LocalDate.of(2023, 11, 25))
                .time(LocalTime.of(7, 30))
                .homeTeamScore(0)
                .awayTeamScore(0)
                .awayTeam(null)
                .homeTeam(null)
                .build();
    }

    public static GamesEntity createGameDec15(){
        return GamesEntity.builder()
                .game_id(2L)
                .date(LocalDate.of(2023, 12, 15))
                .time(LocalTime.of(7, 30))
                .homeTeamScore(0)
                .awayTeamScore(0)
                .awayTeam(null)
                .homeTeam(null)
                .build();
    }

    public static PlayerStatsByGameEntity playerStatsOnNov25(){
        return PlayerStatsByGameEntity.builder()
                .game(null)
                .player(null)
                .assists(6)
                .points(20)
                .offensive_rebounds(4)
                .defensive_rebounds(5)
                .minutes_played(35)
                .build();
    }

    public static PlayerStatsByGameEntity playerStatsOnDec15(){
        return PlayerStatsByGameEntity.builder()
                .game(null)
                .player(null)
                .assists(3)
                .points(26)
                .offensive_rebounds(4)
                .defensive_rebounds(5)
                .minutes_played(35)
                .fouls(2)
                .blocks(1)
                .build();
    }
}
