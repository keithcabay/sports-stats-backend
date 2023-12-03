package com.keith.SportsStats.PlayersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keith.SportsStats.TestData;
import com.keith.SportsStats.domains.entity.PlayersEntity;
import com.keith.SportsStats.domains.entity.TeamsEntity;
import com.keith.SportsStats.services.player_services.PlayersService;
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

import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PlayersControllerIntegrationTests {
    private PlayersService playersService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private TeamsService teamsService;

    @Autowired
    public PlayersControllerIntegrationTests(PlayersService playersService, TeamsService teamsService, MockMvc mockMvc){
        this.playersService = playersService;
        this.teamsService = teamsService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePlayerWithExistingTeamSuccessfullyReturnsHttp201Created() throws Exception {
        PlayersEntity player1 = TestData.createPlayerAonKnicks();

        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity returnedTeam1 = teamsService.save(team1);

        player1.setTeam(returnedTeam1);

        String json = objectMapper.writeValueAsString(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePlayerWithNoTeamSuccessfullyReturnsHttp201Created() throws Exception{
        PlayersEntity playerNoTeam = TestData.createPlayerNoTeam();
        String json = objectMapper.writeValueAsString(playerNoTeam);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePlayerWithTeamThatDoesNotExistReturnsHttp201CreatedAndTeamAsNull() throws Exception{
        PlayersEntity playerNonExistingTeam = TestData.createPlayerNonExistingTeam();
        String json = objectMapper.writeValueAsString(playerNonExistingTeam);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.team", nullValue())
        );
    }

    @Test
    public void testThatCreatedPlayerReturnsSavedPlayer() throws Exception{
        PlayersEntity player1 = TestData.createPlayerBonBucks();
        TeamsEntity teams1 = TestData.createBucksTeam();

        TeamsEntity returnedTeam1 = teamsService.save(teams1);
        player1.setTeam(returnedTeam1);

        String json = objectMapper.writeValueAsString(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").value(player1.getFirst_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").value(player1.getLast_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.team").value(returnedTeam1)
        );
    }

    @Test
    public void testThatGetPlayerByTeamShortNameReturnsHttp200Successfully() throws Exception{
        PlayersEntity player1 = TestData.createPlayerBonBucks();
        TeamsEntity teams1 = TestData.createBucksTeam();

        TeamsEntity returnedTeam1 = teamsService.save(teams1);
        player1.setTeam(returnedTeam1);

        playersService.save(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/teams/" + player1.getTeam().getShortName())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetPlayersByNonExistingTeamShortReturns404NotFound() throws Exception{
         PlayersEntity player1 = TestData.createPlayerAonKnicks();

         playersService.save(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/teams/" + "NON EXISTING")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetPlayersByTeamNameReturnsListOfPlayers() throws Exception{
        PlayersEntity playersKnicks1 = TestData.createPlayerAonKnicks();
        PlayersEntity playersKnicks2 = TestData.createPlayerNoTeam();

        TeamsEntity teamKnicks = TestData.createKnicksTeam();
        TeamsEntity returnTeamKnicks = teamsService.save(teamKnicks);

        playersKnicks1.setTeam(returnTeamKnicks);
        playersKnicks2.setTeam(returnTeamKnicks);

        playersService.save(playersKnicks1);
        playersService.save(playersKnicks2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/teams/" + returnTeamKnicks.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0].team").value(returnTeamKnicks)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[1].team").value(returnTeamKnicks)
        );
    }

    @Test
    public void testThatGetListOfAllPlayersReturnsHttp200Successfully() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        PlayersEntity player2 = TestData.createPlayerBonBucks();
        PlayersEntity player3 = TestData.createPlayerNoTeam();

        playersService.save(player1);
        playersService.save(player2);
        playersService.save(player3);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatGetListOfAllPlayersReturnListOfAllPlayers() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        PlayersEntity player2 = TestData.createPlayerBonBucks();
        PlayersEntity player3 = TestData.createPlayerNoTeam();

        PlayersEntity returnedPlayer1 = playersService.save(player1);
        PlayersEntity returnedPlayer2 = playersService.save(player2);
        PlayersEntity returnedPlayer3 = playersService.save(player3);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0]").value(returnedPlayer1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[1]").value(returnedPlayer2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[2]").value(returnedPlayer3)
        );
    }

    @Test
    public void testThatGetPlayerByIdSuccessfullyReturnsHttp200() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        player1.setPlayer_id(10L);

        playersService.save(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/" + player1.getPlayer_id())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetPlayerByIdReturnsCorrectPlayer() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        player1.setPlayer_id(10L);

        PlayersEntity returnedPlayer1 = playersService.save(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/" + player1.getPlayer_id())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").value(returnedPlayer1.getFirst_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").value(returnedPlayer1.getLast_name())
        );
    }

    @Test
    public void testThatPartialUpdatePlayerSuccessfullyReturns200() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        playersService.save(player1);

        player1.setFirst_name("UPDATED FIRST NAME");
        String json = objectMapper.writeValueAsString(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + player1.getPlayer_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
               MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdatePlayerWithoutTeamReturnsCorrectUpdatedPlayer() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        PlayersEntity returnedPlayer1 = playersService.save(player1);

        player1.setFirst_name("UPDATED FIRST NAME");
        String json = objectMapper.writeValueAsString(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + returnedPlayer1.getPlayer_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").value("UPDATED FIRST NAME")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").value(player1.getLast_name())
        );
    }

    @Test
    public void testThatPartialUpdatePlayerWithTeamReturnsCorrectUpdatedPlayer() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        PlayersEntity returnedPlayer1 = playersService.save(player1);

        TeamsEntity team1 = TestData.createKnicksTeam();
        teamsService.save(team1);

        player1.setTeam(team1);
        String json = objectMapper.writeValueAsString(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + returnedPlayer1.getPlayer_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").value(player1.getFirst_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").value(player1.getLast_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.team").value(team1)
        );
    }

    @Test
    public void testThatPartialUpdatePlayerOnTeamWithNewTeamReturnsCorrectUpdatedPlayer() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        TeamsEntity team1 = TestData.createKnicksTeam();
        teamsService.save(team1);

        PlayersEntity returnedPlayer1 = playersService.save(player1);

        TeamsEntity team2 = TestData.createBucksTeam();
        TeamsEntity returnedTeam2 = teamsService.save(team2);

        player1.setTeam(returnedTeam2);
        String json = objectMapper.writeValueAsString(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + returnedPlayer1.getPlayer_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").value(player1.getFirst_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").value(player1.getLast_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.team").value(returnedTeam2)
        );
    }

    @Test
    public void testThatDeletePlayerByIdReturns204NoContent() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        PlayersEntity returnedPlayer1 = playersService.save(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/" + returnedPlayer1.getPlayer_id())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeletePlayerByIdSuccessfullyDeletesPlayer() throws Exception{
        PlayersEntity player1 = TestData.createPlayerAonKnicks();
        PlayersEntity returnedPlayer1 = playersService.save(player1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/" + returnedPlayer1.getPlayer_id())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.team").doesNotExist()
        );
    }
}
