package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.service.CreateRoomService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CreateRoomServiceImpl implements CreateRoomService {

    @Override
    public Room execute(String title, int owner, int userCount, int problemDif, String password, List<String> problemTags, int timeLimit) {
        if (password != null) {
            if (password.length() > 4) {
                throw new IllegalArgumentException("비밀번호는 4자리여야 합니다.");
            }

            try {
                Integer.parseInt(password);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("비밀번호는 숫자로 이루어져 있어야 합니다.");
            }
        }

        if (userCount != 2 && userCount != 4) {
            throw new IllegalArgumentException("최대 인원 수는 2명 또는 4명이어야 합니다.");
        }

        if (problemDif < 1 || problemDif > 25) {
            throw new IllegalArgumentException("존재하지 않는 난이도 입니다.");
        }

        if (problemTags.size() > 5) {
            throw new IllegalArgumentException("태그는 최대 5개까지 선택해야 합니다.");
        }

        Set<String> set = new HashSet<>(problemTags);

        if (problemTags.size() != set.size()) {
            throw new IllegalArgumentException("중복된 태그는 선택 할 수 없습니다.");
        }

        if (timeLimit < 10 || timeLimit > 120) {
            throw new IllegalArgumentException("제한시간은 10분 부터 120분사이로 설정해야 합니다.");
        }

        if (timeLimit % 10 != 0) {
            throw new IllegalArgumentException("제한시간은 10분 단위로 설정해야 합니다.");
        }

        return new Room(title, owner, userCount, problemDif, password, problemTags, timeLimit);
    }
}
