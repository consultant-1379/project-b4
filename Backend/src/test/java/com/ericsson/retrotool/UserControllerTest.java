package com.ericsson.retrotool;

import com.ericsson.retrotool.controller.UserController;
import com.ericsson.retrotool.model.LoginCredsDTO;
import com.ericsson.retrotool.model.Team;
import com.ericsson.retrotool.model.User;
import com.ericsson.retrotool.model.UserDTO;
import com.ericsson.retrotool.repository.TeamRepository;
import com.ericsson.retrotool.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void get_all_users_endpoint_should_work_correctly() throws Exception {

        User user1 = new User(1,"Shruti","shruti@ericsson.com","shr123","Developer");
        User user2 = new User(2,"Amrutha","Amrutha@ericsson.com","amu123","Developer");

        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);

        given(userRepository.findAll()).willReturn(allUsers);

        mockMvc.perform(get("/getUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is(user1.getUserId())))
                .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
                .andExpect(jsonPath("$[1].userId", is(user2.getUserId())))
                .andExpect(jsonPath("$[1].username", is(user2.getUsername())));
    }

    @Test
    void user_signup_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        Team team = new Team(1,"General", userSet, boardSet);

        UserDTO userSignUp = new UserDTO(1,"Shruti","shruti@gmail.com","shr123","Developer",1);

        User user = new User();
        UserDTO userDTO = new UserDTO();

        given(modelMapper.map(any(),any())).willReturn(user);
        given(teamRepository.findById(userSignUp.getTeamId())).willReturn(java.util.Optional.of(team));
        given(userRepository.save(user)).willReturn(user);
        given(modelMapper.map(any(),any())).willReturn(userDTO);

        mockMvc.perform(post("/signup")
                .content(asJsonString(userSignUp))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userSignUp.getUserId())))
                .andExpect(jsonPath("$.username", is(userSignUp.getUsername())));
    }

    @Test
    void user_login_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        Team team = new Team(1,"General", userSet, boardSet);

        LoginCredsDTO userCredDTO = new LoginCredsDTO("Shruti","shr123");

        UserDTO userLoginDTO = new UserDTO(1,"Shruti","shruti@gmail.com","shr123","Developer",1);

        User userLogin = new User();

        modelMapper.map(userLoginDTO,userLogin);

        UserDTO userDTO = new UserDTO();

        given(userRepository.findByUsernameAndPassword(userLogin.getUsername(), userLogin.getPassword())).willReturn(userLogin);
        given(modelMapper.map(any(),any())).willReturn(userDTO);

        mockMvc.perform(post("/login")
                .content(asJsonString(userCredDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userLogin.getUserId())))
                .andExpect(jsonPath("$.username", is(userLogin.getUsername())));
    }

    @Test
    void update_team_endpoint_should_work_correctly() throws Exception {

        Set userSet = new HashSet<>();

        Set boardSet = new HashSet<>();

        Team team = new Team(2,"Group 2", userSet, boardSet);

        User user = new User(1,"Shruti","shruti@gmail.com","shr123","Developer");
        UserDTO updatedUserDTO = new UserDTO(1,"Shruti","shruti@gmail.com","shr123","Developer",2);

        User updatedUser = new User();

        modelMapper.map(updatedUserDTO,updatedUser);

        given(userRepository.findById(1)).willReturn(java.util.Optional.of(user));
        given(teamRepository.findById(2)).willReturn(java.util.Optional.of(team));
        given(userRepository.save(updatedUser)).willReturn(updatedUser);
        given(userRepository.findById(1)).willReturn(java.util.Optional.of(user));
        given(teamRepository.findById(2)).willReturn(java.util.Optional.of(team));
        given(userRepository.save(any())).willReturn(updatedUser);
        given(modelMapper.map(any(),any())).willReturn(updatedUserDTO);

        mockMvc.perform(put("/updateTeam")
                .param("userId", "1")
                .param("teamId", "2")
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
