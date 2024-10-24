package com.ganada.whothefast.domain.room.presentation;

import com.ganada.whothefast.domain.room.presentation.dto.request.CreateRoomRequest;
import com.ganada.whothefast.domain.room.service.CreateRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final CreateRoomService createRoomService;

    @PostMapping("/create")
    public ResponseEntity<Void> createRoom(@RequestBody CreateRoomRequest request) {
        createRoomService.execute(
                request.getTitle(),
                request.getOwner(),
                request.getUserCount(),
                request.getProblemDif(),
                request.getPassword(),
                request.getProblemTags(),
                request.getTimeLimit()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
