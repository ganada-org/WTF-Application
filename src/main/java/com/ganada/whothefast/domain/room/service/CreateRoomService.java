package com.ganada.whothefast.domain.room.service;

import com.ganada.whothefast.domain.room.entity.Room;
import com.ganada.whothefast.domain.room.presentation.dto.request.CreateRoomRequest;

public interface CreateRoomService {

    Room execute(CreateRoomRequest request);
}
