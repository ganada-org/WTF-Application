package com.ganada.whothefast.room;

import com.ganada.whothefast.domain.room.entity.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoomTest {

    @Test
    @DisplayName("방 객체가 생성되면 성공")
    public void createRoom() {
        // Given

        // When
        Room room = new Room();

        // Then
        assertNotNull(room); // room이 null이 아닌지 확인
    }
}
