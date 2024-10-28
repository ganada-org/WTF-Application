package com.ganada.whothefast.domain.room.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
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
}
