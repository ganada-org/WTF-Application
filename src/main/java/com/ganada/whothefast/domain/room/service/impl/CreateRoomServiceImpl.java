package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.presentation.dto.request.CreateRoomRequest;
import com.ganada.whothefast.domain.room.service.CreateRoomService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreateRoomServiceImpl implements CreateRoomService {

    @Override
    public Room execute(CreateRoomRequest request) {
        if (request.getPassword() != null) {
            if (request.getPassword().length() > 4) {
                throw new IllegalArgumentException("비밀번호는 4자리여야 합니다.");
            }

            try {
                Integer.parseInt(request.getPassword());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("비밀번호는 숫자로 이루어져 있어야 합니다.");
            }
        }

        if (request.getUserCount() != 2 && request.getUserCount() != 4) {
            throw new IllegalArgumentException("최대 인원 수는 2명 또는 4명이어야 합니다.");
        }

        if (request.getProblemDif() < 1 || request.getProblemDif() > 25) {
            throw new IllegalArgumentException("존재하지 않는 난이도 입니다.");
        }

        if (request.getProblemTags().size() > 5) {
            throw new IllegalArgumentException("태그는 최대 5개까지 선택해야 합니다.");
        }

        Set<String> set = new HashSet<>(request.getProblemTags());

        if (request.getProblemTags().size() != set.size()) {
            throw new IllegalArgumentException("중복된 태그는 선택 할 수 없습니다.");
        }

        if (request.getTimeLimit() < 10 || request.getTimeLimit() > 120) {
            throw new IllegalArgumentException("제한시간은 10분 부터 120분사이로 설정해야 합니다.");
        }

        if (request.getTimeLimit() % 10 != 0) {
            throw new IllegalArgumentException("제한시간은 10분 단위로 설정해야 합니다.");
        }

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("방 제목이 비어있습니다.");
        }

        return new Room(request.getTitle(),
                request.getOwner(),
                request.getUserCount(),
                request.getProblemDif(),
                request.getPassword(),
                request.getProblemTags(),
                request.getTimeLimit(),
                "WAITING"
        );
    }
}
