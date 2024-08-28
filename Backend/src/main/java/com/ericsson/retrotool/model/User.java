package com.ericsson.retrotool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;

    private String username;
    private String email;
    private String password;
    private String position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="teamId")
    @JsonBackReference
    private Team team;

    public User(int userId, String username, String email, String password, String position, Team team) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.position = position;
        this.team = team;
    }

    public User(int userId, String username, String email, String password, String position) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.position = position;
    }

    public User() {}

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
