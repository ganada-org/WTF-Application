package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.service.CreateRoomService;

import java.util.List;

public class CreateRoomServiceImpl implements CreateRoomService {

    @Override
    public Room execute(String title, int owner, int userCount, int problemDif, String password, List<String> problemTags, int timeLimit) {
        return new Room(title, owner, userCount, problemDif, password, problemTags, timeLimit);
    }
}
