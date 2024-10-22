package com.ganada.whothefast.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
