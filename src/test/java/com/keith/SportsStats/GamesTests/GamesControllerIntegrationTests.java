package com.keith.SportsStats.GamesTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keith.SportsStats.TestData;
import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.services.game_services.GamesService;
import com.keith.SportsStats.services.team_services.TeamsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.format.DateTimeFormatter;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GamesControllerIntegrationTests {

    private MockMvc mockMvc;
    private GamesService gamesService;
    private TeamsService teamsService;
    private ObjectMapper objectMapper;

    @Autowired
    public GamesControllerIntegrationTests(MockMvc mockMvc, GamesService gamesService, TeamsService teamsService, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.gamesService = gamesService;
        this.teamsService = teamsService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatAddNewGameSuccessfullyReturns201CreatedIfTeamsExist() throws Exception{
        GamesEntity games1 = TestData.createGameNov25();

        TeamsEntity team1 = TestData.createBucksTeam();
        TeamsEntity team2 = TestData.createKnicksTeam();

        teamsService.save(team1);
        teamsService.save(team2);

        String json = objectMapper.writeValueAsString(games1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/games/NYK/MIL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatAddNewGameSuccessfullyReturns204NotFoundIfTeamsDoesNotExist() throws Exception{
        GamesEntity games1 = TestData.createGameNov25();

        String json = objectMapper.writeValueAsString(games1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/games/NYK/MIL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatAddNewGameWithExistingTeamsReturnsCorrectGame() throws Exception{
        GamesEntity game1 = TestData.createGameNov25();
        TeamsEntity team1 = TestData.createBucksTeam();
        TeamsEntity team2 = TestData.createKnicksTeam();

        teamsService.save(team1);
        teamsService.save(team2);

        String json = objectMapper.writeValueAsString(game1);

        //when calling toString on time it drops the seconds, so format is needed
        String formattedTime = game1.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/games/NYK/MIL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.game_id").value(game1.getGame_id())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.awayTeam").value(team1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.homeTeam").value(team2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.date").value(game1.getDate().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.time").value(formattedTime)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.awayTeamScore").value(game1.getAwayTeamScore())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.homeTeamScore").value(game1.getHomeTeamScore())
        );
    }
}
