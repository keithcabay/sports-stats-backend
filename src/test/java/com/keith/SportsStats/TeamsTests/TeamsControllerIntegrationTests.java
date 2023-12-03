package com.keith.SportsStats.TeamsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keith.SportsStats.TestData;
import com.keith.SportsStats.domains.entity.TeamsEntity;
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
public class TeamsControllerIntegrationTests {

    private TeamsService teamsService;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public TeamsControllerIntegrationTests(TeamsService teamsService, MockMvc mockMvc){
        this.teamsService = teamsService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateTeamSuccessfullyReturns201Created() throws Exception {
        TeamsEntity teamsEntity = TestData.createKnicksTeam();
        String json = objectMapper.writeValueAsString(teamsEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTeamReturnsSavedTeam() throws Exception {
        TeamsEntity teamsEntity = TestData.createBucksTeam();
        String json = objectMapper.writeValueAsString(teamsEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.shortName").value("MIL")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value("Milwaukee Bucks")
        );
    }

    @Test
    public void testThatGetAllTeamsSuccessfullyReturnHttp200Successful() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAllTeamsReturnsCorrectListOfTeams() throws Exception {
        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity team2 = TestData.createBucksTeam();

        TeamsEntity returnedTeam1 = teamsService.save(team1);
        TeamsEntity returnedTeam2 = teamsService.save(team2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0].shortName").value(returnedTeam1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0].fullName").value(returnedTeam1.getFullName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[1].shortName").value(returnedTeam2.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[1].fullName").value(returnedTeam2.getFullName())
        );
    }

    @Test
    public void testThatGetTeamByNameReturnsHttp200SuccessfulIfFound() throws Exception {
        TeamsEntity team1 = TestData.createKnicksTeam();
        teamsService.save(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams/" + team1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetTeamByNameReturnsHttp200SuccessfulIfNotFound() throws Exception {
        TeamsEntity team1 = TestData.createKnicksTeam();
        teamsService.save(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams/" + 9999)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetTeamByNameReturnsCorrectTeam() throws Exception {
        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity returnedTeam1 = teamsService.save(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/teams/" + returnedTeam1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.shortName").value(returnedTeam1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(returnedTeam1.getFullName())
        );
    }

    @Test
    public void testThatFullUpdateTeamReturnsHttp200Successful() throws Exception{
        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity returnedTeam1 = teamsService.save(team1);

        team1.setFullName("UPDATED FULL NAME");
        String json = objectMapper.writeValueAsString(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/teams/" + returnedTeam1.getShortName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateTeamReturnsUpdatedTeamKeepingShortName() throws Exception{
        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity returnedTeam1 = teamsService.save(team1);

        team1.setShortName("UPDATED SHORT NAME");
        team1.setFullName("UPDATED FULL NAME");
        String json = objectMapper.writeValueAsString(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/teams/" + returnedTeam1.getShortName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.shortName").value(returnedTeam1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value("UPDATED FULL NAME")
        );

    }

    @Test
    public void testThatDeleteSuccessfullyReturnsHttp204NoContent() throws Exception{
        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity returnedTeam1 = teamsService.save(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/teams/" + returnedTeam1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteSuccessfullyDeletesTeam() throws Exception{
        TeamsEntity team1 = TestData.createKnicksTeam();
        TeamsEntity returnedTeam1 = teamsService.save(team1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/teams/" + returnedTeam1.getShortName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.shortName").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").doesNotExist()
        );
    }

}
