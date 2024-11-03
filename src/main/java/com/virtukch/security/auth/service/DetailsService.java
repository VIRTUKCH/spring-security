package com.virtukch.security.auth.service;

import com.virtukch.security.auth.model.DetailsUser;
import com.virtukch.security.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 로그인 요청 시 사용자의 정보를 조회하는 서비스 클래스입니다.
 * 이 클래스는 사용자 ID를 기반으로 데이터베이스에서 사용자 정보를 검색합니다.
 *
 * @author Virtus_Chae
 */
@Service
public class DetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public DetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인 요청 시 사용자 ID를 받아 데이터베이스에서 사용자의 정보를 가져옵니다.
     *
     * @param username 사용자 ID
     * @return UserDetails 사용자 엔티티
     * @throws UsernameNotFoundException 사용자가 존재하지 않는 경우 발생
     * @throws AuthenticationServiceException 사용자 ID가 비어 있는 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null || username.isEmpty()) {
            throw new AuthenticationServiceException(username + " is Empty"); // 사용자 ID가 비어 있는 경우 예외 발생
        } else {
            return userService.findUser(username)
                .map(data -> new DetailsUser(Optional.of(data))) // 사용자 정보를 DetailsUser로 변환
                .orElseThrow(() -> new AuthenticationServiceException(username)); // 사용자가 존재하지 않는 경우 예외 발생
        }
    }
}