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
}
