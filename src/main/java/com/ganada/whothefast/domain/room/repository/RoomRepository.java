package com.ganada.whothefast.domain.room.repository;

import com.ganada.whothefast.domain.room.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
}
