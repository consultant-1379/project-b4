package com.ericsson.retrotool.controller;

import com.ericsson.retrotool.model.RetroBoard;
import com.ericsson.retrotool.model.RetroBoardDTO;
import com.ericsson.retrotool.repository.RetroBoardRepository;
import com.ericsson.retrotool.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BoardController {
    @Autowired
    private RetroBoardRepository boardRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path="/addBoard")
    public ResponseEntity<RetroBoard> addNewItem (@RequestBody RetroBoardDTO boardDTO) {
        RetroBoard board = new RetroBoard();
        modelMapper.map(boardDTO,board);
        board.setTeam(teamRepository.findById(boardDTO.getTeamId()).orElse(null));
        board = boardRepository.save(board);
        return ResponseEntity.ok().body(board);
    }

    @GetMapping(path="/getItems")
    public ResponseEntity<Optional<RetroBoard>> getItemsByBoard(@RequestParam int boardId) {
        return ResponseEntity.ok().body(boardRepository.findById(boardId));

    }
}
