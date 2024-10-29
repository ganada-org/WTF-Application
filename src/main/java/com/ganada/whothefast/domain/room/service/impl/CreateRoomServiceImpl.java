package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.cache.RoomIdCache;
import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.entity.enums.RoomStatus;
import com.ganada.whothefast.domain.room.presentation.dto.request.CreateRoomRequest;
import com.ganada.whothefast.domain.room.repository.RoomRepository;
import com.ganada.whothefast.domain.room.service.CreateRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateRoomServiceImpl implements CreateRoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room execute(CreateRoomRequest request) {
        validateRequest(request);

        Room room = Room.builder()
                .id(RoomIdCache.getId())
                .title(request.getTitle())
                .owner(request.getOwner())
                .userCount(request.getUserCount())
                .problemDif(request.getProblemDif())
                .password(request.getPassword())
                .problemTags(request.getProblemTags())
                .timeLimit(request.getTimeLimit())
                .status(RoomStatus.WAITING)
                .build();

        roomRepository.save(room);
        return room;
    }

    private void validateRequest(CreateRoomRequest request) {
        validatePassword(request.getPassword());
        validateUserCount(request.getUserCount());
        validateProblemDifficulty(request.getProblemDif());
        validateProblemTags(request.getProblemTags());
        validateTimeLimit(request.getTimeLimit());
        validateTitle(request.getTitle());
    }

    private void validatePassword(String password) {
        if (password != null && (password.length() != 4 || !password.chars().allMatch(Character::isDigit))) {
            throw new IllegalArgumentException("비밀번호는 4자리의 숫자여야 합니다.");
        }
    }

    private void validateUserCount(int userCount) {
        if (userCount != 2 && userCount != 4) {
            throw new IllegalArgumentException("최대 인원 수는 2명 또는 4명이어야 합니다.");
        }
    }

    private void validateProblemDifficulty(int problemDif) {
        if (problemDif < 1 || problemDif > 25) {
            throw new IllegalArgumentException("존재하지 않는 난이도 입니다.");
        }
    }

    private void validateProblemTags(List<String> problemTags) {
        if (problemTags.size() > 5) {
            throw new IllegalArgumentException("태그는 최대 5개까지 선택해야 합니다.");
        }
        if (problemTags.size() != new HashSet<>(problemTags).size()) {
            throw new IllegalArgumentException("중복된 태그는 선택 할 수 없습니다.");
        }
    }

    private void validateTimeLimit(int timeLimit) {
        if (timeLimit < 10 || timeLimit > 120) {
            throw new IllegalArgumentException("제한시간은 10분 부터 120분사이로 설정해야 합니다.");
        }

        if (timeLimit % 10 != 0) {
            throw new IllegalArgumentException("제한시간은 10분 단위로 설정해야 합니다.");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("방 제목이 비어있습니다.");
        }
    }
}
