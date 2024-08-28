package com.ericsson.retrotool;

import com.ericsson.retrotool.controller.TeamController;
import com.ericsson.retrotool.model.Team;
import com.ericsson.retrotool.model.TeamDTO;
import com.ericsson.retrotool.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void get_all_teams_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        Team team1 = new Team(1,"General", userSet, boardSet);
        Team team2 = new Team(2,"Group 2", userSet, boardSet);

        List<Team> allTeams = new ArrayList<>();
        allTeams.add(team1);
        allTeams.add(team2);

        given(teamRepository.findAll()).willReturn(allTeams);

        mockMvc.perform(get("/getTeams")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].teamId", is(team1.getTeamId())))
                .andExpect(jsonPath("$[0].teamName", is(team1.getTeamName())))
                .andExpect(jsonPath("$[1].teamId", is(team2.getTeamId())))
                .andExpect(jsonPath("$[1].teamName", is(team2.getTeamName())));
    }

    @Test
    void get_team_by_ID_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        Team team = new Team(1,"General", userSet, boardSet);

        given(teamRepository.findById(1)).willReturn(java.util.Optional.of(team));

        mockMvc.perform(get("/team/{teamId}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId").value(1));
    }

    @Test
    void create_team_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        TeamDTO teamDTO = new TeamDTO(1,"General", userSet, boardSet);
        Team team = new Team(1,"General", userSet, boardSet);

        given(modelMapper.map(any(),any())).willReturn(team);
        given(teamRepository.save(any())).willReturn(team);

        mockMvc.perform(post("/createTeam")
                .content(asJsonString(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId", is(teamDTO.getTeamId())))
                .andExpect(jsonPath("$.teamName", is(teamDTO.getTeamName())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
