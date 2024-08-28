package com.ericsson.retrotool.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team {
    @Id
    @Column(name="teamId")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int teamId;

    private String teamName;

    @JsonManagedReference
    @OneToMany(mappedBy="team")
    private Set<User> users;

    @JsonManagedReference
    @OneToMany(mappedBy="team")
    @OrderBy("boardId DESC")
    private Set<RetroBoard> boards;

    public Set<RetroBoard> getBoards() {
        return boards;
    }

    public void setBoards(Set<RetroBoard> boards) {
        this.boards = boards;
    }

    public Team(int teamId, String teamName, Set<User> users, Set<RetroBoard> boards) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.users = users;
        this.boards = boards;
    }

    public Team() {}

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
