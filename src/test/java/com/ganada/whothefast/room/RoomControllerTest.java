package com.ganada.whothefast.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("방 생성 API 테스트")
    public void create_room_api_test() throws Exception {
        String jsonRequest = "{"
                + "\"title\": \"Test Room\","
                + "\"owner\": 1,"
                + "\"userCount\": 4,"
                + "\"problemDif\": 1,"
                + "\"password\": \"1234\","
                + "\"problemTags\": [\"tag1\", \"tag2\"],"
                + "\"timeLimit\": 60"
                + "}";

        mockMvc.perform(post("/room/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }
}
