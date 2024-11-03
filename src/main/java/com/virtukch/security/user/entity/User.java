package com.virtukch.security.user.entity;

import com.virtukch.security.common.VirtusRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 사용자 정보를 나타내는 엔티티 클래스입니다.
 * 이 클래스는 데이터베이스의 사용자 테이블에 매핑됩니다.
 *
 * @author Virtus_Chae
 */
@Entity
@Table(name = "TBL_USER")
public class User {

    @Id
    @Column(name = "USER_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNo; // 사용자 번호

    @Column(name = "USER_ID")
    private String userId; // 사용자 ID

    @Column(name = "USER_PASS")
    private String userPass; // 사용자 비밀번호

    @Column(name = "USER_NAME")
    private String userName; // 사용자 이름

    @Column(name = "USER_EMAIL")
    private String userEmail; // 사용자 이메일

    @Enumerated(value = EnumType.STRING)
    @Column(name = "USER_ROLE")
    private VirtusRole role; // 사용자 역할

    @Column(name = "USER_STATE")
    private String state; // 사용자 상태

    /**
     * 사용자의 역할 목록을 반환하는 메서드입니다.
     *
     * @return 역할 목록
     */
    public List<String> getRoleList() {
        if (!this.role.getRole().isEmpty()) {
            return Arrays.asList(this.role.getRole().split(",")); // 역할을 쉼표로 분리하여 리스트로 반환
        }
        return new ArrayList<>(); // 역할이 없는 경우 빈 리스트 반환
    }

    // 기본 생성자
    public User() {
    }

    // Getter 및 Setter 메서드
    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public VirtusRole getRole() {
        return role;
    }

    public void setRole(VirtusRole role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
            "userNo=" + userNo +
            ", userId='" + userId + '\'' +
            ", userPass='" + userPass + '\'' +
            ", userName='" + userName + '\'' +
            ", userEmail='" + userEmail + '\'' +
            ", role=" + role +
            ", state='" + state + '\'' +
            '}';
    }
}