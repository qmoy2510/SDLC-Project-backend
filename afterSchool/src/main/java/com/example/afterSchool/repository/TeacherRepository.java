package com.example.afterSchool.repository;

import com.example.afterSchool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // 로그인용: 이메일로 교사 조회
    Optional<Teacher> findByEmail(String email);

    // 회원가입용: 이메일 중복 체크
    boolean existsByEmail(String email);

    Optional<Teacher> findByPhoneNumber(String phoneNumber);
}