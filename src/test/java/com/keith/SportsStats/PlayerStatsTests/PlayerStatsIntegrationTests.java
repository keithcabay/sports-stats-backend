package com.keith.SportsStats.PlayerStatsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keith.SportsStats.TestData;
import com.keith.SportsStats.domains.entity.GamesEntity;
import com.keith.SportsStats.domains.entity.PlayerStatsByGameEntity;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.services.game_services.GamesService;
import com.keith.SportsStats.services.player_services.PlayersService;
import com.keith.SportsStats.services.player_stats_services.PlayerStatsService;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PlayerStatsIntegrationTests {

    private PlayerStatsService playerStatsService;

    private PlayersService playersService;

    private GamesService gamesService;

    private TeamsService teamsService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public PlayerStatsIntegrationTests(PlayerStatsService playerStatsService, PlayersService playersService
            , TeamsService teamsService, MockMvc mockMvc, ObjectMapper objectMapper, GamesService gamesService) {
        this.playerStatsService = playerStatsService;
        this.playersService = playersService;
        this.teamsService = teamsService;
        this.gamesService = gamesService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatAddPlayerStatSuccessfullyReturns201Created() throws Exception{
        PlayersEntity player = TestData.createPlayerAonKnicks();
        PlayersEntity returnedPlayer = playersService.save(player);

        TeamsEntity homeTeam = TestData.createKnicksTeam();
        TeamsEntity returnedHomeTeam = teamsService.save(homeTeam);

        TeamsEntity awayTeam = TestData.createBucksTeam();
        TeamsEntity returnedAwayTeam = teamsService.save(awayTeam);

        GamesEntity game = TestData.createGameNov25();
        GamesEntity returnedGame = gamesService.save(game, returnedHomeTeam.getShortName(), returnedAwayTeam.getShortName());

        PlayerStatsByGameEntity playerStat = TestData.playerStatsOnNov25();
        playerStat.setGame(returnedGame);
        playerStat.setPlayer(returnedPlayer);

        String json = objectMapper.writeValueAsString(playerStat);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/player-stats/" + returnedPlayer.getPlayer_id() + "/" + returnedGame.getGame_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatAddPlayerStatWithNoTeamAndNoGameReturns404NotFound() throws Exception{
        PlayerStatsByGameEntity playerStat = TestData.playerStatsOnNov25();
        String json = objectMapper.writeValueAsString(playerStat);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/player-stats/999/998")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatAddPlayerStatReturnsCorrectPlayerStat() throws Exception{
        PlayersEntity player = TestData.createPlayerAonKnicks();
        PlayersEntity returnedPlayer = playersService.save(player);

        TeamsEntity homeTeam = TestData.createKnicksTeam();
        TeamsEntity returnedHomeTeam = teamsService.save(homeTeam);

        TeamsEntity awayTeam = TestData.createBucksTeam();
        TeamsEntity returnedAwayTeam = teamsService.save(awayTeam);

        GamesEntity game = TestData.createGameNov25();
        GamesEntity returnedGame = gamesService.save(game, returnedHomeTeam.getShortName(), returnedAwayTeam.getShortName());

        PlayerStatsByGameEntity playerStat = TestData.playerStatsOnNov25();
        playerStat.setGame(returnedGame);
        playerStat.setPlayer(returnedPlayer);

        String json = objectMapper.writeValueAsString(playerStat);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/player-stats/" + returnedPlayer.getPlayer_id() + "/" + returnedGame.getGame_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.game.game_id").value(returnedGame.getGame_id())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.player.player_id").value(returnedPlayer.getPlayer_id())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.points").value(playerStat.getPoints())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.offensive_rebounds").value(playerStat.getOffensive_rebounds())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.defensive_rebounds").value(playerStat.getDefensive_rebounds())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.minutes_played").value(playerStat.getMinutes_played())
        );
    }
}
