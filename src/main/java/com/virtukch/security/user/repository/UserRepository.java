package com.virtukch.security.user.repository;

import com.virtukch.security.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * 이 인터페이스는 Spring Data JPA의 JpaRepository를 확장하여
 * 기본 CRUD 작업을 지원합니다.
 *
 * @author Virtus_Chae
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 주어진 사용자 ID로 사용자를 검색하는 메서드입니다.
     *
     * @param id 검색할 사용자 ID
     * @return Optional<User> 찾은 사용자 정보가 포함된 Optional 객체
     */
    Optional<User> findByUserId(String id);
}