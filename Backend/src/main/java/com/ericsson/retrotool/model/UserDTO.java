package com.ericsson.retrotool.model;

public class UserDTO {

    private int userId;

    private String username;
    private String email;
    private String password;
    private String position;

    private int teamId;

    public UserDTO(int userId, String username, String email, String password, String position, int teamId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.position = position;
        this.teamId = teamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public UserDTO() {
    }
}
