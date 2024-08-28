package com.ericsson.retrotool.model;

import java.util.ArrayList;

public class ItemDTO {
    private int id;
    private String itemType;
    private String vote;
    private ArrayList<String> comments;
    private String summary;
    private String description;
    private String priority;
    private int boardId;


    public ItemDTO(int id, String itemType, String vote, String summary, String description, String priority, int boardId) {
        this.id = id;
        this.itemType = itemType;
        this.vote = vote;
        this.comments = new ArrayList<String>();
        this.summary = summary;
        this.description = description;
        this.priority = priority;
        this.boardId = boardId;
    }

    public ItemDTO(){
        this.comments = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public ArrayList<String> getComments() { return comments; }

    public void setComments(ArrayList<String> comments) { this.comments = comments; }

    public void addComments(String comment) { this.comments.add(comment); }

    public String getSummary() { return summary; }

    public void setSummary(String summary) { this.summary = summary; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }

    public void setPriority(String priority) { this.priority = priority; }
}
