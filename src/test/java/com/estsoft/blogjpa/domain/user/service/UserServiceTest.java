package com.estsoft.blogjpa.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.blogjpa.domain.user.dto.UserRequestDto;
import com.estsoft.blogjpa.domain.user.entity.User;
import com.estsoft.blogjpa.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

//    @Test
//    public void saveTest() {
//        //given :
//        UserRequestDto request = new UserRequestDto("mock_email","mock_pw");
//
//        //when :
//        User user = userService.save(request);
//
//        //then :
//        assertThat(user.getEmail()).isEqualTo("mock_email");
//        assertThat(user.getPassword()).isEqualTo("mock_email");
//
//    }
//
//    @Test
//    public void existsTest() {
//        boolean exists = userRepository.existsById(1L);
//
//        System.out.println(exists);
//        //org.junit.jupiter.api.Assertions.assertTrue(exists);
//    }
}