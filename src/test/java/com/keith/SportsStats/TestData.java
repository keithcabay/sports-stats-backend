package com.keith.SportsStats;

import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;

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
        return PlayersEntity.builder()
                .player_id(1L)
                .first_name("Jalen")
                .last_name("Brunson")
                .team(null)
                .build();
    }

    public static PlayersEntity createPlayerBonBucks(){
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
                // in database but does have a TeamsEntity object including shortName
                .build();
    }
}
