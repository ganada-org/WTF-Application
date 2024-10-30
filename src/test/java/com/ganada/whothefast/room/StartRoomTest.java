package com.ganada.whothefast.room;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.entity.enums.RoomStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StartRoomTest {

    @Test
    @DisplayName("방 status 값이 START 로 바뀌면 성공")
    public void room_status_change() {
        // Given
        Room room = Room.builder()
                .status(RoomStatus.WAITING)
                .build();

        // When
        room.setStatus(RoomStatus.START);

        // Then
        assertEquals(RoomStatus.START, room.getStatus());
    }
    
    @Test
    @DisplayName("방 시작 시 status 값이 START 로 바뀌면 성공")
    public void start_room_status_change() {
        // Given
        Room room = Room.builder()
                .status(RoomStatus.WAITING)
                .build();

        // When
        startRoomService(room);

        // Then
        assertEquals(RoomStatus.START, room.getStatus());
    }
}
