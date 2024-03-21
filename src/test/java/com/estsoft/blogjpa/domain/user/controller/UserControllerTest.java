package com.estsoft.blogjpa.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.estsoft.blogjpa.domain.user.dto.UserRequestDto;
import com.estsoft.blogjpa.domain.user.entity.User;
import com.estsoft.blogjpa.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc //MockMvc 생성 및 자동 구성
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ac;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {//MockMvc인스턴스 초기화
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
    }

    @Test
    void signup() throws Exception{
        //given : 회원가입에 필요한 정보 초기화
        UserRequestDto request = new UserRequestDto("mock_email","mock_pw");

        //when : POST /user
        ResultActions response = mockMvc.perform(post("/user")
            .param("email",request.getEmail())
            .param("password",request.getPassword()));

        //then : 호출 결과 HTTP Status code 300,user 저장 여부 검사
        response.andExpect(status().is3xxRedirection());

        //회원가입이 됐다면 그게 정말 DB에 저장됐는지 확인해야 함
        User byEmail = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Assertions.assertNotNull(byEmail); //명시적으로 null이 아님을 검증

    }
}