package com.ericsson.retrotool.controller;

import com.ericsson.retrotool.model.ItemDTO;
import com.ericsson.retrotool.model.RetroItem;
import com.ericsson.retrotool.repository.ItemRepository;
import com.ericsson.retrotool.repository.RetroBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RetroBoardRepository boardRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path="/addItem")
    public ResponseEntity<RetroItem> addNewItem (@RequestBody ItemDTO itemDTO) {
        RetroItem item = new RetroItem();
        modelMapper.map(itemDTO,item);
        item.setBoard(boardRepository.findById(itemDTO.getBoardId()).orElse(null));
        item = itemRepository.save(item);
        return ResponseEntity.ok().body(item);
    }

    @PutMapping(path="/upvoteItem")
    public ResponseEntity<ItemDTO> upvoteItem(@RequestParam int itemId) {

        Optional<RetroItem> item = itemRepository.findById(itemId);
        if(item.isPresent()) {
            item.get().setVote(String.valueOf(Integer.valueOf(item.get().getVote()) + 1));
            RetroItem modifiedItem = itemRepository.save(item.get());
            ItemDTO itemDTO = new ItemDTO();
            modelMapper.map(modifiedItem,itemDTO);
            return ResponseEntity.ok().body(itemDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path="/downvoteItem")
    public ResponseEntity<ItemDTO> downvoteItem(@RequestParam int itemId) {

        Optional<RetroItem> item = itemRepository.findById(itemId);
        if(item.isPresent()) {
            item.get().setVote(String.valueOf(Integer.valueOf(item.get().getVote()) - 1));
            RetroItem modifiedItem = itemRepository.save(item.get());
            ItemDTO itemDTO = new ItemDTO();
            modelMapper.map(modifiedItem,itemDTO);
            return ResponseEntity.ok().body(itemDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path="/updateItemType")
    public ResponseEntity<ItemDTO> updateItemType(@RequestParam int itemId, @RequestParam String itemType) {

        Optional<RetroItem> item = itemRepository.findById(itemId);
        if(item.isPresent()) {
            item.get().setItemType(String.valueOf(itemType));
            RetroItem modifiedItem = itemRepository.save(item.get());
            ItemDTO itemDTO = new ItemDTO();
            modelMapper.map(modifiedItem,itemDTO);
            return ResponseEntity.ok().body(itemDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path="/addComments")
    public ResponseEntity<ItemDTO> addComments(@RequestParam int itemId,@RequestParam String comment) {

        Optional<RetroItem> item = itemRepository.findById(itemId);
        if(item.isPresent()) {
            item.get().addComments(comment);
            RetroItem modifiedItem = itemRepository.save(item.get());
            ItemDTO itemDTO = new ItemDTO();
            modelMapper.map(modifiedItem,itemDTO);
            return ResponseEntity.ok().body(itemDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }



}
