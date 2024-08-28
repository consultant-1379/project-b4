package com.ericsson.retrotool.controller;

import com.ericsson.retrotool.model.LoginCredsDTO;
import com.ericsson.retrotool.model.Team;
import com.ericsson.retrotool.model.User;
import com.ericsson.retrotool.model.UserDTO;
import com.ericsson.retrotool.repository.TeamRepository;
import com.ericsson.retrotool.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path="/login")
    public ResponseEntity<UserDTO> getUser(@RequestBody LoginCredsDTO loginCredsDTO) {
        User user = userRepository.findByUsernameAndPassword(loginCredsDTO.getUsername(), loginCredsDTO.getPassword());
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user, userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping(path="/updateTeam")
    public ResponseEntity<UserDTO> updateTeam(@RequestParam int userId, @RequestParam int teamId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Team> team = teamRepository.findById(teamId);
        if(user.isPresent() && team.isPresent()) {
            user.get().setTeam(team.get());
            User modifiedUser = userRepository.save(user.get());
            UserDTO userDTO = new UserDTO();
            modelMapper.map(modifiedUser,userDTO);
            return ResponseEntity.ok().body(userDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        modelMapper.map(userDTO,user);
        user.setTeam(teamRepository.findById(userDTO.getTeamId()).orElse(null));
        user = userRepository.save(user);
        modelMapper.map(user,userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping(path="/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

}
