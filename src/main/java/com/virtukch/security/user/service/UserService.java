package com.virtukch.security.user.service;

import com.virtukch.security.user.entity.User;
import com.virtukch.security.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스입니다. 이 클래스는 사용자 데이터에 대한 조회 및 처리를 담당합니다.
 *
 * @author Virtus_Chae
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 사용자 ID로 사용자를 검색하는 메서드입니다.
     *
     * @param id 검색할 사용자 ID
     * @return Optional<User> 찾은 사용자 정보가 포함된 Optional 객체
     */
    public Optional<User> findUser(String id) {
        return userRepository.findByUserId(id);
    }
}