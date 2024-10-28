package com.ganada.whothefast.room;

import com.ganada.whothefast.domain.room.cache.RoomIdCache;
import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.presentation.dto.request.CreateRoomRequest;
import com.ganada.whothefast.domain.room.service.CreateRoomService;
import com.ganada.whothefast.domain.room.service.impl.CreateRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateRoomTest {

    private CreateRoomService createRoomService;

    @BeforeEach
    public void setUp() {
        createRoomService = new CreateRoomServiceImpl();
        RoomIdCache.setId(1);
    }

    private CreateRoomRequest createRoomRequest(String title, Integer owner, Integer userCount, Integer problemDif, String password, List<String> problemTags, Integer timeLimit) {
        return CreateRoomRequest.builder()
                .title(title)
                .owner(owner)
                .userCount(userCount)
                .problemDif(problemDif)
                .password(password)
                .problemTags(problemTags)
                .timeLimit(timeLimit)
                .build();
    }

    private void assertThrowsWithMessage(CreateRoomRequest request, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("방 객체가 모든 속성이 존재한 상태로 생성되면 성공")
    public void create_room_with_required_fields() {
        // Given
        CreateRoomRequest request = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag2"), 60);

        // When
        Room room = createRoomService.execute(request);

        // Then
        assertNotNull(room);
        assertEquals("Test Room", room.getTitle());
        assertEquals(1, room.getOwner());
        assertEquals(2, room.getUserCount());
        assertEquals(1, room.getProblemDif());
        assertEquals("1234", room.getPassword());
        assertEquals(List.of("tag1", "tag2"), room.getProblemTags());
        assertEquals(60, room.getTimeLimit());
        assertEquals("WAITING", room.getStatus());
    }

    @Test
    @DisplayName("방이 비밀번호 없이 생성되면 성공")
    public void create_room_without_password() {
        // Given
        CreateRoomRequest request = createRoomRequest("Test Room", 1, 2, 1, null, List.of("tag1", "tag2"), 60);

        // When
        Room room = createRoomService.execute(request);

        // Then
        assertNotNull(room);
        assertNull(room.getPassword());
    }

    @Test
    @DisplayName("비밀번호 형식을 지키지 않은 방 생성 시 예외 발생")
    public void create_room_password_validation() {
        // Given
        CreateRoomRequest request1 = createRoomRequest("Test Room", 1, 2, 1, "12345", List.of("tag1", "tag2"), 60);
        CreateRoomRequest request2 = createRoomRequest("Test Room", 1, 2, 1, "abcd", List.of("tag1", "tag2"), 60);

        // When & Then
        assertThrowsWithMessage(request1, "비밀번호는 4자리여야 합니다.");
        assertThrowsWithMessage(request2, "비밀번호는 숫자로 이루어져 있어야 합니다.");
    }

    @Test
    @DisplayName("최대 인원 수가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_user_count_validation() {
        // Given
        CreateRoomRequest request = createRoomRequest("Test Room", 1, 5, 1, "1234", List.of("tag1", "tag2"), 60);

        // When & Then
        assertThrowsWithMessage(request, "최대 인원 수는 2명 또는 4명이어야 합니다.");
    }

    @Test
    @DisplayName("문제 난이도가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_problem_dif_validation() {
        // Given
        CreateRoomRequest request = createRoomRequest("Test Room", 1, 2, 0, "1234", List.of("tag1", "tag2"), 60);

        // When && Then
        assertThrowsWithMessage(request, "존재하지 않는 난이도 입니다.");
    }

    @Test
    @DisplayName("문제 태그 수가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_problem_tags_validation() {
        // Given
        CreateRoomRequest request1 = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag2", "tag3", "tag4", "tag5", "tag6"), 60);
        CreateRoomRequest request2 = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag1"), 60);

        // When && Then
        assertThrowsWithMessage(request1, "태그는 최대 5개까지 선택해야 합니다.");
        assertThrowsWithMessage(request2, "중복된 태그는 선택 할 수 없습니다.");
    }

    @Test
    @DisplayName("시간 제한이 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_time_limit_validation() {
        // Given
        CreateRoomRequest request1 = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag2"), 130);
        CreateRoomRequest request2 = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag2"), 61);

        // When & Then
        assertThrowsWithMessage(request1, "제한시간은 10분 부터 120분사이로 설정해야 합니다.");
        assertThrowsWithMessage(request2, "제한시간은 10분 단위로 설정해야 합니다.");
    }

    @Test
    @DisplayName("방 생성 시 방ID 값이 자동으로 등록되면 성공")
    public void room_id_automatic_registration() {
        // Given
        CreateRoomRequest request = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag2"), 60);

        // When
        Room room = createRoomService.execute(request);

        // Then
        assertEquals(1, room.getId());
    }

    @Test
    @DisplayName("방이 생성될 때 마다 방ID 값이 증가하면 성공")
    public void room_id_auto_increment() {
        // Given
        CreateRoomRequest request = createRoomRequest("Test Room", 1, 2, 1, "1234", List.of("tag1", "tag2"), 60);

        // When
        Room room1 = createRoomService.execute(request);
        Room room2 = createRoomService.execute(request);

        // Then
        assertEquals(1, room1.getId());
        assertEquals(2, room2.getId());
    }

    @Test
    @DisplayName("방 제목이 null 또는 공백인 경우 실패")
    public void room_title_validation() {
        // Given
        CreateRoomRequest request1 = createRoomRequest(null, 1, 2, 1, "1234", List.of("tag1", "tag2"), 60);
        CreateRoomRequest request2 = createRoomRequest(" ", 1, 2, 1, "1234", List.of("tag1", "tag2"), 60);

        // When & Then
        assertThrowsWithMessage(request1, "방 제목이 비어있습니다.");
        assertThrowsWithMessage(request2, "방 제목이 비어있습니다.");
    }
}
