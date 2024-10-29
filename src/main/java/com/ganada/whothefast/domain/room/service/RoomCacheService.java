package com.ganada.whothefast.domain.room.service;

import com.ganada.whothefast.domain.room.entity.Room;

public interface RoomCacheService {

    void saveRoom(Room room);

    Room findRoomById(int id);
}
