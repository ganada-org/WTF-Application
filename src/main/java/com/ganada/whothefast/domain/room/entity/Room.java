package com.ganada.whothefast.domain.room.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Room {

    private String title;

    private int owner;

    private int userCount;

    private int problemDif;

    private String password;

    private List<String> problemTags;

    private int timeLimit;

    public Room() { }

    public Room(String title, int owner, int userCount, int problemDif, String password, List<String> problemTags, int timeLimit) {
        this.title = title;
        this.owner = owner;
        this.userCount = userCount;
        this.problemDif = problemDif;
        this.password = password;
        this.problemTags = problemTags;
        this.timeLimit = timeLimit;
    }
}
