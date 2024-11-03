package com.virtukch.security.auth.model.dto;

/**
 * 회원이 로그인할 때 전달하는 정보입니다. 이 DTO(Data Transfer Object)는 로그인 요청 시 사용자의 ID와 비밀번호를 포함합니다.
 *
 * @author Virtus_Chae
 */
public class LoginDto {

    private String id;
    private String pass;

    public String getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
            "id='" + id + '\'' +
            ", pass='" + pass + '\'' +
            '}';
    }
}