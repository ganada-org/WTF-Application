package com.ganada.whothefast.domain.room.presentation.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRoomRequest {

    private String title;

    private int owner;

    private int userCount;

    private int problemDif;

    private String password;

    private List<String> problemTags;

    private int timeLimit;
}
