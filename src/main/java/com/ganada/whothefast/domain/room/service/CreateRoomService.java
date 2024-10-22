package com.ganada.whothefast.domain.room.service;

import com.ganada.whothefast.domain.room.entity.Room;

import java.util.List;

public interface CreateRoomService {

    Room execute(String title, int owner, int userCount, int problemDif, String password, List<String> problemTags, int timeLimit);
}
