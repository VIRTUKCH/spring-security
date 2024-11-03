package com.virtukch.security.common;

/**
 * 사용자 역할을 정의하는 열거형 클래스입니다.
 * 이 클래스는 시스템 내에서 사용자의 권한을 관리하는 데 사용됩니다.
 *
 * @author Virtus_Chae
 */
public enum VirtusRole {

    USER("USER"), // 일반 사용자 역할
    ADMIN("ADMIN"), // 관리자 역할
    ALL("USER,ADMIN"); // 모든 역할

    private String role; // 역할 이름

    /**
     * 역할을 설정하는 생성자입니다.
     *
     * @param role 역할 이름
     */
    VirtusRole(String role) {
        this.role = role; // 역할 이름 초기화
    }

    /**
     * 역할 이름을 반환합니다.
     *
     * @return 역할 이름
     */
    public String getRole() {
        return role; // 역할 이름 반환
    }
}