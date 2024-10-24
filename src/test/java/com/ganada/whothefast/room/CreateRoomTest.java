package com.ganada.whothefast.room;

import com.ganada.whothefast.domain.room.cache.RoomIdCache;
import com.ganada.whothefast.domain.room.entity.Room;
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
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1; // 브5 = 1, 브4 = 2, 루1 = 25
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        // When
        Room room = createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit);

        // Then
        assertNotNull(room); // room이 null이 아닌지 확인
        assertEquals(title, room.getTitle()); // title 값 확인
        assertEquals(owner, room.getOwner()); // owner 값 확인
        assertEquals(userCount, room.getUserCount()); // userCount 값 확인
        assertEquals(problemDif, room.getProblemDif()); // problemDif 값 확인
        assertEquals(password, room.getPassword()); // password 값 확인
        assertEquals(problemTags, room.getProblemTags()); // problemTags 값 확인
        assertEquals(timeLimit, room.getTimeLimit()); // timeLimit 값 확인
    }

    @Test
    @DisplayName("방이 비밀번호 없이 생성되면 성공")
    public void create_room_without_password() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        String password = null;
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        // When
        Room room = createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit);

        // Then
        assertNotNull(room); // room이 null이 아닌지 확인
        assertNull(room.getPassword()); // password가 null 인지 확인
    }

    @Test
    @DisplayName("비밀번호 형식을 지키지 않은 방 생성 시 예외 발생")
    public void create_room_password_validation() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        String password1 = "12345";
        String password2 = "abcd";

        // When & Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password1, problemTags, timeLimit)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password2, problemTags, timeLimit)
        );

        assertEquals("비밀번호는 4자리여야 합니다.", exception1.getMessage());
        assertEquals("비밀번호는 숫자로 이루어져 있어야 합니다.", exception2.getMessage());
    }

    @Test
    @DisplayName("최대 인원 수가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_user_count_validation() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 5;
        int problemDif = 1;
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit)
        );

        assertEquals("최대 인원 수는 2명 또는 4명이어야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("문제 난이도가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_problem_dif_validation() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 0;
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        // When && Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit)
        );

        assertEquals("존재하지 않는 난이도 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("문제 태그 수가 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_problem_tags_validation() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        String password = "1234";
        int timeLimit = 60;

        List<String> problemTags1 = List.of("tag1", "tag2", "tag3", "tag4", "tag5", "tag6");
        List<String> problemTags2 = List.of("tag1", "tag1");

        // When && Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password, problemTags1, timeLimit)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password, problemTags2, timeLimit)
        );

        assertEquals("태그는 최대 5개까지 선택해야 합니다.", exception1.getMessage());
        assertEquals("중복된 태그는 선택 할 수 없습니다.", exception2.getMessage());
    }

    @Test
    @DisplayName("시간 제한이 유효하지 않은 방 생성 시 예외 발생")
    public void create_room_time_limit_validation() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");

        int timeLimit1 = 130;
        int timeLimit2 = 61;

        // When & Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit1)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit2)
        );

        assertEquals("제한시간은 10분 부터 120분사이로 설정해야 합니다.", exception1.getMessage());
        assertEquals("제한시간은 10분 단위로 설정해야 합니다.", exception2.getMessage());
    }

    @Test
    @DisplayName("방 생성 시 방ID 값이 자동으로 등록되면 성공")
    public void room_id_automatic_registration() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        // When
        Room room = createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit);

        // Then
        assertEquals(1, room.getId());
    }

    @Test
    @DisplayName("방이 생성될 때 마다 방ID 값이 증가하면 성공")
    public void room_id_auto_increment() {
        // Given
        String title = "Test Room";
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        // When
        Room room1 = createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit);
        Room room2 = createRoomService.execute(title, owner, userCount, problemDif, password, problemTags, timeLimit);

        // Then
        assertEquals(1, room1.getId());
        assertEquals(2, room2.getId());
    }

    @Test
    @DisplayName("방 제목이 null 또는 공백인 경우 실패")
    public void room_title_validation() {
        // Given
        int owner = 1;
        int userCount = 2;
        int problemDif = 1;
        String password = "1234";
        List<String> problemTags = List.of("tag1", "tag2");
        int timeLimit = 60;

        String title1 = null;
        String title2 = "";

        // When & Then
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title1, owner, userCount, problemDif, password, problemTags, timeLimit)
        );

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                createRoomService.execute(title2, owner, userCount, problemDif, password, problemTags, timeLimit)
        );

        assertEquals("방 제목이 비어있습니다.", exception1.getMessage());
        assertEquals("방 제목이 비어있습니다.", exception2.getMessage());
    }
}
