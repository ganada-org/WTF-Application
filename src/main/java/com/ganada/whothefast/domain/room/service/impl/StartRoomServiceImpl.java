package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.entity.enums.RoomStatus;
import com.ganada.whothefast.domain.room.service.StartRoomService;
import org.springframework.stereotype.Service;

@Service
public class StartRoomServiceImpl implements StartRoomService {

    @Override
    public void execute(Room room) {
        room.setStatus(RoomStatus.START);
    }
}
