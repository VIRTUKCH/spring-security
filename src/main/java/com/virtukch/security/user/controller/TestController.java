package com.virtukch.security.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트용 컨트롤러 클래스입니다.
 * 이 클래스는 인증된 사용자만 접근할 수 있는 테스트 엔드포인트를 제공합니다.
 *
 * @author Virtus_Chae
 */
@Controller
@PreAuthorize("hasAuthority('USER')") // USER 권한이 있는 사용자만 접근 가능
@RestController
public class TestController {

    /**
     * 테스트 GET 요청을 처리하는 메서드입니다.
     *
     * @return "test" 문자열
     */
    @GetMapping("/test")
    public String test() {
        return "test"; // "test" 문자열 반환
    }

    /**
     * 테스트 POST 요청을 처리하는 메서드입니다.
     *
     * @return "test" 문자열
     */
    @PostMapping("/test")
    public String test2() {
        return "test"; // "test" 문자열 반환
    }
}