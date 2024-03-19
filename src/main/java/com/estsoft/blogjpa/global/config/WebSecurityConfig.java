package com.estsoft.blogjpa.global.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {//실제 인증처리를 하는 시큐리티 설정 파일

    @Bean
    public WebSecurityCustomizer configure() {//특정 요청을 스프링 시큐리티의 보안 검사에서 제외한다
        //H2 DB 콘솔에 대한 접근을 허용한다. /static으로 시작하는 모든 요청을 보안 검사에서 제외한다(css,js,이미지파일 등)
        return web -> web.ignoring().requestMatchers(toH2Console()).requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {//애플리케이션의 HTTP 보안을 구성한다
        return httpSecurity.authorizeHttpRequests(auth ->//HTTP 요청에 대한 보안을 구성한다
            auth.requestMatchers("/login", "/signup", "/user").permitAll() //login,signup,user 경로에 대한 요청은 인증 없이 접근이 허용된다
                .anyRequest().authenticated()) //위에 정의되지 않은 모든 다른 요청은 인증된 사용자만 접근이 가능하다
            .formLogin(auth -> auth.loginPage("/login") //폼 기반 로그인을 구성한다. 로그인 페이지의 경로는 /login으로 하고 , 이경로에 대한 요청은 자동으로 인증 없이 허용된다
            .defaultSuccessUrl("/articles")) //로그인에 성공하면 사용자는 /articles로 리다이렉트 된다
            .logout(auth -> auth.logoutSuccessUrl("/login") //로그아웃을 구성하며 로그아웃에 성공하면 /login으로 리다이렉트된다
            .invalidateHttpSession(true)) //로그아웃 시 HTTP 세션을 무효화 한다
            .build();

    }

    @Bean //메서드가 반환하는 객체를 스프링 컨테이너가 관리하는 빈으로 등록한다.
    public BCryptPasswordEncoder bCryptPasswordEncoder() {//password encoder로 사용할 BCryptPasswordEncoder 객체를 빈으로 등록
        return new BCryptPasswordEncoder();
    }
}
