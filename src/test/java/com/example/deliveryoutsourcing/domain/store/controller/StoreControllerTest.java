package com.example.deliveryoutsourcing.domain.store.controller;

import com.example.deliveryoutsourcing.domain.store.service.StoreService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.domain.store.dto.StoreRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사장님은_가게_생성_가능() throws Exception {
        // given
        CustomUserDetails ownerDetails = new CustomUserDetails(
            1L, "owner@test.com", "password",
            Collections.singleton(() -> "ROLE_OWNER")
        );

        StoreRequestDto.Create requestDto = new StoreRequestDto.Create();
        requestDto.setName("가게 이름");
        requestDto.setOpenTime("09:00");
        requestDto.setCloseTime("22:00");
        requestDto.setMinOrderPrice(10000);

        // when & then
        mockMvc.perform(post("/stores")
            .with(authentication(new UsernamePasswordAuthenticationToken(ownerDetails, "", ownerDetails.getAuthorities())))
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isCreated());  // 응답코드 201
    }

    @Test
    void 일반_유저는_가게_생성_못함() throws Exception {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(
            2L, "user@test.com", "password",
            Collections.singleton(() -> "ROLE_USER")
        );

        StoreRequestDto.Create requestDto = new StoreRequestDto.Create();
        requestDto.setName("가게 이름");
        requestDto.setOpenTime("09:00");
        requestDto.setCloseTime("22:00");
        requestDto.setMinOrderPrice(10000);

        // when & then
        mockMvc.perform(post("/stores")
            .with(authentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities())))
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden()); // 응답코드 403 에러 나야함
    }
}
