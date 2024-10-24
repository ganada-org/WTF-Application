package com.ganada.whothefast.domain.room.cache;

public class RoomIdCache {

    private static int id = 1;

    public static int getId() {
        return id++;
    }

    public static void setId(int id) {
        RoomIdCache.id = id;
    }
}
