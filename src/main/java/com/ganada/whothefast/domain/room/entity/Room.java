package com.ganada.whothefast.domain.room.entity;

import com.ganada.whothefast.domain.room.entity.enums.RoomStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Builder
@RedisHash("rooms")
public class Room {

    @Id
    private int id;

    private String title;

    private int owner;

    private int userCount;

    private int problemDif;

    private String password;

    private List<String> problemTags;

    private int timeLimit;

    private RoomStatus status;

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
