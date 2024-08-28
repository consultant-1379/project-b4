package com.ericsson.retrotool.controller;

import com.ericsson.retrotool.model.Team;
import com.ericsson.retrotool.model.TeamDTO;
import com.ericsson.retrotool.repository.TeamRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path="/getTeams")
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok().body(teamRepository.findAll());
    }

    @GetMapping(path="/team/{teamId}")
    public ResponseEntity<Optional<Team>> getTeam(@PathVariable int teamId) {
        return ResponseEntity.ok().body(teamRepository.findById(teamId));
    }

    @PostMapping(path="/createTeam")
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        modelMapper.map(teamDTO,team);
        team = teamRepository.save(team);
        return ResponseEntity.ok().body(team);
    }

}
