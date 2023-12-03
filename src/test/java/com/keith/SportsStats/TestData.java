package com.keith.SportsStats;

import com.keith.SportsStats.domains.entity.TeamsEntity;

import java.util.ArrayList;

public class TestData {
    private TestData(){}

    public static TeamsEntity createKnicksTeam(){
        return TeamsEntity.builder()
                .shortName("NY")
                .fullName("New York Knicks")
                .players(null)
                .build();
    }

    public static TeamsEntity createBucksTeam(){
        return TeamsEntity.builder()
                .shortName("MIL")
                .fullName("Milwaukee Bucks")
                .players(new ArrayList<>())
                .build();
    }
}
