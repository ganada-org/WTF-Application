package com.ganada.whothefast.domain.room.entity;

import com.ganada.whothefast.domain.room.cache.RoomIdCache;
import lombok.Getter;

import java.util.List;

@Getter
public class Room {

    private int id;

    private String title;

    private int owner;

    private int userCount;

    private int problemDif;

    private String password;

    private List<String> problemTags;

    private int timeLimit;

    private String status;

    public Room(String title, int owner, int userCount, int problemDif, String password, List<String> problemTags, int timeLimit, String status) {
        this.id = RoomIdCache.getId();
        this.title = title;
        this.owner = owner;
        this.userCount = userCount;
        this.problemDif = problemDif;
        this.password = password;
        this.problemTags = problemTags;
        this.timeLimit = timeLimit;
        this.status = status;
    }
}
