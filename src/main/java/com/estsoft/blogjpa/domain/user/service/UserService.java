package com.estsoft.blogjpa.domain.user.service;

import com.estsoft.blogjpa.domain.user.dto.UserRequestDto;
import com.estsoft.blogjpa.domain.user.entity.User;
import com.estsoft.blogjpa.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public void save(UserRequestDto request) {//폼에 입력받은 email,pw를 암호화해서 DB에 저장
        userRepository.save(new User(request.getEmail(),encoder.encode(request.getPassword())));
    }
}
