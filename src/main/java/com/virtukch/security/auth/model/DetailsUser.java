package com.virtukch.security.auth.model;

import com.virtukch.security.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * 사용자 세부 정보를 나타내는 클래스입니다.
 * 이 클래스는 Spring Security의 UserDetails 인터페이스를 구현하여
 * 사용자 정보를 인증 및 권한 부여에 사용합니다.
 *
 * @author Virtus_Chae
 */
public class DetailsUser implements UserDetails {

    private User user; // 사용자 정보

    /**
     * 기본 생성자입니다.
     */
    public DetailsUser() {
    }

    /**
     * Optional<User> 객체를 사용하여 DetailsUser 를 생성하는 생성자입니다.
     *
     * @param user Optional<User> 객체
     */
    public DetailsUser(Optional<User> user) {
        this.user = user.get();
    }

    /**
     * 사용자 정보를 반환합니다.
     *
     * @return User 객체
     */
    public User getUser() {
        return user;
    }

    /**
     * 사용자 정보를 설정합니다.
     *
     * @param user User 객체
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(role -> authorities.add(() -> role));
        return authorities; // 사용자 권한 목록을 반환
    }

    @Override
    public String getPassword() {
        return user.getUserPass(); // 사용자의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return user.getUserId(); // 사용자의 ID 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 나타냄
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았음을 나타냄
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않았음을 나타냄
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되어 있음을 나타냄
    }
}