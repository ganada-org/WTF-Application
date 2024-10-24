package com.ganada.whothefast.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganada.whothefast.domain.room.presentation.dto.request.CreateRoomRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("방 생성 API 테스트")
    public void create_room_api_test() throws Exception {
        // Given
        CreateRoomRequest request = CreateRoomRequest.builder()
                .title("Test Room")
                .owner(1)
                .userCount(4)
                .problemDif(1)
                .password("1234")
                .problemTags(List.of("tag1", "tag2"))
                .timeLimit(60)
                .build();

        String json = mapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post("/room/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
