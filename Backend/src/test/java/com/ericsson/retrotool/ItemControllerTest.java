package com.ericsson.retrotool;

import com.ericsson.retrotool.controller.ItemController;
import com.ericsson.retrotool.model.ItemDTO;
import com.ericsson.retrotool.model.RetroItem;
import com.ericsson.retrotool.model.RetroBoard;
import com.ericsson.retrotool.model.Team;
import com.ericsson.retrotool.repository.ItemRepository;
import com.ericsson.retrotool.repository.RetroBoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private RetroBoardRepository retroBoardRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void add_item_endpoint_should_work_correctly() throws Exception {

        Set itemSet = new HashSet<>();

        Team team = new Team();

        RetroBoard board = new RetroBoard(1,"Sprint 1",itemSet,team);
        ArrayList<String> comments = new ArrayList<String>();
        ItemDTO item = new ItemDTO(1,"Glad","1", "Trail","whatever whatever", "1", 1);
        RetroItem retroItem = new RetroItem(1,"Glad","1","Trail","whatever whatever", "1");

        given(modelMapper.map(any(),any())).willReturn(retroItem);
        given(retroBoardRepository.findById(item.getBoardId())).willReturn(java.util.Optional.of(board));
        given(itemRepository.save(any())).willReturn(retroItem);

        mockMvc.perform(post("/addItem")
                .content(asJsonString(item))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId())))
                .andExpect(jsonPath("$.itemType", is(item.getItemType())));
    }

    @Test
    void upvote_item_endpoint_should_work_correctly() throws Exception {

        Set itemSet = new HashSet<>();

        Team team = new Team();

        RetroBoard board = new RetroBoard(1,"Sprint 1",itemSet,team);

        RetroItem retroItem = new RetroItem(1,"Glad","1","whatever", "whatever whatever", "1");
        RetroItem updatedRetroItem = new RetroItem(1,"Glad","2","whatever", "whatever whatever", "1");

        ItemDTO itemDTO = new ItemDTO();

        given(itemRepository.findById(1)).willReturn(java.util.Optional.of(retroItem));
        given(itemRepository.save(any())).willReturn(updatedRetroItem);
        given(modelMapper.map(any(),any())).willReturn(itemDTO);

        mockMvc.perform(put("/upvoteItem")
                .param("itemId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void downvote_item_endpoint_should_work_correctly() throws Exception {

        Set itemSet = new HashSet<>();

        Team team = new Team();

        RetroBoard board = new RetroBoard(1,"Sprint 1",itemSet,team);

        RetroItem retroItem = new RetroItem(1,"Glad","1","whatever", "whatever whatever", "1");

        given(itemRepository.findById(1)).willReturn(java.util.Optional.of(retroItem));

        mockMvc.perform(put("/downvoteItem")
                .param("itemId", "1")
                .content(asJsonString(retroItem))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
