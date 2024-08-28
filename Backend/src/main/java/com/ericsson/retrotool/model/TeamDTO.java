package com.ericsson.retrotool.model;

import java.util.Set;

public class TeamDTO {
    private int teamId;

    private String teamName;
    private Set<User> users;

    private Set<RetroBoard> boards;

    public TeamDTO(int teamId, String teamName, Set<User> users, Set<RetroBoard> boards) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.users = users;
        this.boards = boards;
    }

    public Set<RetroBoard> getBoards() {
        return boards;
    }

    public void setBoards(Set<RetroBoard> boards) {
        this.boards = boards;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
