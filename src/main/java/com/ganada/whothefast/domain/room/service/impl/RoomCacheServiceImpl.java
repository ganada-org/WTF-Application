package com.ganada.whothefast.domain.room.service.impl;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.service.RoomCacheService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomCacheServiceImpl implements RoomCacheService {

    private final ConcurrentHashMap<Integer, Room> roomCache = new ConcurrentHashMap<>();

    @Override
    public void saveRoom(Room room) {
        roomCache.put(room.getId(), room);
    }

    @Override
    public Room findRoomById(int id) {
        return roomCache.get(id);
    }
}
