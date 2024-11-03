package com.virtukch.security.user.controller;

import com.virtukch.security.user.entity.User;
import com.virtukch.security.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 사용자 관련 요청을 처리하는 컨트롤러 클래스입니다. 이 클래스는 사용자 회원가입 기능을 제공합니다.
 *
 * @author Virtus_Chae
 */
@Controller
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository; // 사용자 정보를 처리하는 레포지토리

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더

    /**
     * 사용자 회원가입을 처리하는 메서드입니다.
     *
     * @param user 회원가입할 사용자 정보
     * @return ResponseEntity 회원가입 결과에 대한 응답
     */
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user) {
        // 비밀번호를 암호화하고 상태를 설정
        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        user.setState("Y");
        userRepository.save(user); // 사용자 정보를 저장

        return ResponseEntity.ok("회원가입 성공"); // 성공 시 메시지 반환
    }
}
