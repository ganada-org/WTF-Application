package com.ganada.whothefast.room;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.service.CreateRoomService;
import com.ganada.whothefast.domain.room.service.impl.CreateRoomServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoomTest {

    @Test
    @DisplayName("방 객체가 모든 속성이 존재한 상태로 생성되면 성공")
    public void create_room_with_required_fields() {
        // Given
        CreateRoomService createRoomService = new CreateRoomServiceImpl();

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
        String password = null;

        // When
        Room room = new Room(password);

        // Then
        assertNotNull(room); // room이 null이 아닌지 확인
        assertNull(room.getPassword()); // password가 null 인지 확인
    }
}
