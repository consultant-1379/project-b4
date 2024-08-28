package com.ericsson.retrotool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class RetroItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String itemType;
    private String vote;
    private ArrayList<String> comments;
    private String summary;
    private String description;
    private String priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="boardId")
    @JsonBackReference
    private RetroBoard board;

    public RetroItem() {
        this.comments = new ArrayList<String>();
    }

    public RetroBoard getBoard() {
        return board;
    }

    public void setBoard(RetroBoard board) {
        this.board = board;
    }

    public RetroItem(int id, String itemType, String vote, String summary, String description, String priority) {
        this.id = id;
        this.itemType = itemType;
        this.vote = vote;
        this.comments = new ArrayList<String>();;
        this.summary = summary;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
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
