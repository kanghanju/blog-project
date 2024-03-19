package com.estsoft.blogjpa.domain.user.controller;

import com.estsoft.blogjpa.domain.user.dto.UserRequestDto;
import com.estsoft.blogjpa.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor //초기화 되지 않은 final 필드나 @NotNull이 붙은 필드에 대해 생성자를 생성해준다
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(@ModelAttribute UserRequestDto request) {
        userService.save(request);
        return "redirect:/login"; //로그인 실패시 /login으로 리다이렉트 된다
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request,response, //세션 종료 및 쿠키 정리
            SecurityContextHolder.getContext().getAuthentication()); //SecurityContectHolder에서 현재 사용자의 인증 정보 제거
        return "redirect:/login";
    }
}
