package com.ericsson.retrotool;

import com.ericsson.retrotool.controller.BoardController;
import com.ericsson.retrotool.model.RetroBoard;
import com.ericsson.retrotool.model.RetroBoardDTO;
import com.ericsson.retrotool.model.Team;
import com.ericsson.retrotool.repository.RetroBoardRepository;
import com.ericsson.retrotool.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetroBoardRepository retroBoardRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void get_all_items_by_board_ID_endpoint_should_work_correctly() throws Exception {

        Set itemSet = new HashSet<>();

        Team team = new Team();

        RetroBoard board = new RetroBoard(1,"Sprint 1",itemSet,team);

        int boardId = 1;

        given(retroBoardRepository.findById(boardId)).willReturn(java.util.Optional.of(board));

        mockMvc.perform(get("/getItems")
                .param("boardId", String.valueOf(boardId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardId").value(board.getBoardId()))
                .andExpect(jsonPath("$.boardDesc").value(board.getBoardDesc()));
    }

    @Test
    void add_board_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        Team team = new Team(1,"General", userSet, boardSet);

        Set itemSet = new HashSet<>();

        RetroBoardDTO retroBoardDTO = new RetroBoardDTO(1,"Sprint-1", itemSet, 1);

        RetroBoard board = new RetroBoard();

        given(teamRepository.findById(retroBoardDTO.getBoardId())).willReturn(java.util.Optional.of(team));
        given(modelMapper.map(any(),any())).willReturn(board);
        given(retroBoardRepository.save(any())).willReturn(board);

        mockMvc.perform(post("/addBoard")
                .content(asJsonString(retroBoardDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardId").value(board.getBoardId()))
                .andExpect(jsonPath("$.boardDesc").value(board.getBoardDesc()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
