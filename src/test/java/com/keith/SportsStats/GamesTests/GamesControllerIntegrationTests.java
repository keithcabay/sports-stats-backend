package com.keith.SportsStats.GamesTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keith.SportsStats.services.game_services.GamesService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GamesControllerIntegrationTests {

    private MockMvc mockMvc;
    private GamesService gamesService;

    private ObjectMapper objectMapper;

    public GamesControllerIntegrationTests(MockMvc mockMvc, GamesService gamesService){
        this.mockMvc = mockMvc;
        this.gamesService = gamesService;
        this.objectMapper = new ObjectMapper();
    }
}
