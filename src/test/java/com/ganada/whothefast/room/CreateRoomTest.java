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

    @Test
    @DisplayName("방 객체가 모든 속성이 존재한 상태로 생성되면 성공")
    public void create_room_with_required_fields() {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

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
    }

    @Test
    @DisplayName("방이 비밀번호 없이 생성되면 성공")
    public void create_room_without_password() {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password(null)
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

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
        CreateRoomRequest request1 = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("12345")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        CreateRoomRequest request2 = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("abcd")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        // When & Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request1)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request2)
        );

        assertEquals("비밀번호는 4자리여야 합니다.", exception1.getMessage());
        assertEquals("비밀번호는 숫자로 이루어져 있어야 합니다.", exception2.getMessage());
    }

    @Test
    @DisplayName("최대 인원 수가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_user_count_validation() {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(5)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request)
        );

        assertEquals("최대 인원 수는 2명 또는 4명이어야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("문제 난이도가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_problem_dif_validation() {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(0)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        // When && Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request)
        );

        assertEquals("존재하지 않는 난이도 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("문제 태그 수가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_problem_tags_validation() {
        // Given
        CreateRoomRequest request1 = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2", "tag3", "tag4", "tag5", "tag6"))
                .timeLimit(60)
                .build();

        CreateRoomRequest request2 = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag1"))
                .timeLimit(60)
                .build();

        // When && Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request1)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request2)
        );

        assertEquals("태그는 최대 5개까지 선택해야 합니다.", exception1.getMessage());
        assertEquals("중복된 태그는 선택 할 수 없습니다.", exception2.getMessage());
    }

    @Test
    @DisplayName("시간 제한이 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_time_limit_validation() {
        // Given
        CreateRoomRequest request1 = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(130)
                .build();

        CreateRoomRequest request2 = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(61)
                .build();

        // When & Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request1)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request2)
        );

        assertEquals("제한시간은 10분 부터 120분사이로 설정해야 합니다.", exception1.getMessage());
        assertEquals("제한시간은 10분 단위로 설정해야 합니다.", exception2.getMessage());
    }

    @Test
    @DisplayName("방 생성 시 방ID 값이 자동으로 등록되면 성공")
    public void room_id_automatic_registration() {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        // When
        Room room = createRoomService.execute(request);

        // Then
        assertEquals(1, room.getId());
    }

    @Test
    @DisplayName("방이 생성될 때 마다 방ID 값이 증가하면 성공")
    public void room_id_auto_increment() {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

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
        CreateRoomRequest request1 = CreateRoomRequest.builder()
                .title(null)
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        CreateRoomRequest request2 = CreateRoomRequest.builder()
                .title(" ")
                .owner(1)
                .userCount(2)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        // When & Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request1)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(request2)
        );

        assertEquals("방 제목이 비어있습니다.", exception1.getMessage());
        assertEquals("방 제목이 비어있습니다.", exception2.getMessage());
    }
}
