package com.ericsson.retrotool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RetroBoard {
    @Id
    @Column(name="boardId")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int boardId;

    private String boardDesc;

    @JsonManagedReference
    @OneToMany(mappedBy="board")
    private Set<RetroItem> items;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="teamId")
    @JsonBackReference
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public RetroBoard() {}

    public RetroBoard(int boardId, String boardDesc, Set<RetroItem> items, Team team) {
        this.boardId = boardId;
        this.boardDesc = boardDesc;
        this.items = items;
        this.team = team;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardDesc() {
        return boardDesc;
    }

    public void setBoardDesc(String boardDesc) {
        this.boardDesc = boardDesc;
    }

    public Set<RetroItem> getItems() {
        return items;
    }

    public void setItems(Set<RetroItem> items) {
        this.items = items;
    }
}
