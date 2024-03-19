package com.estsoft.blogjpa.domain.user.service;

import com.estsoft.blogjpa.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //사용자의 이메일을 기반으로 UserDetails 객체를 반환한다.
    //UserDetails는 Spring Security가 사용자 인증과 권한 부여 과정에서 사용하는 인터페이스로, 사용자의 정보를 포함한다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email:" + email));
    }
}
