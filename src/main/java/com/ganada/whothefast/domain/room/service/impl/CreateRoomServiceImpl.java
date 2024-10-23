package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.service.CreateRoomService;

import java.util.List;

public class CreateRoomServiceImpl implements CreateRoomService {

    @Override
    public Room execute(String title, int owner, int userCount, int problemDif, String password, List<String> problemTags, int timeLimit) {
        if (password.length() > 4) {
            throw new IllegalArgumentException("비밀번호는 4자리여야 합니다.");
        }

        try {
            Integer.parseInt(password);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("비밀번호는 숫자로 이루어져 있어야 합니다.");
        }

        if (userCount != 2 && userCount != 4) {
            throw new IllegalArgumentException("최대 인원 수는 2명 또는 4명이어야 합니다.");
        }

        if (problemDif < 1 || problemDif > 25) {
            throw new IllegalArgumentException("존재하지 않는 난이도 입니다.");
        }

        return new Room(title, owner, userCount, problemDif, password, problemTags, timeLimit);
    }
}
