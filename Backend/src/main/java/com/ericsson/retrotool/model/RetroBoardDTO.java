package com.ericsson.retrotool.model;

import java.util.Set;

public class RetroBoardDTO {
    private int boardId;

    private String boardDesc;
    private Set<RetroItem> items;
    private int teamId;

    public RetroBoardDTO(int boardId, String boardDesc, Set<RetroItem> items, int teamId) {
        this.boardId = boardId;
        this.boardDesc = boardDesc;
        this.items = items;
        this.teamId = teamId;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
