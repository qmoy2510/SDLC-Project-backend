package com.example.afterSchool.repository;

import com.example.afterSchool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // 로그인용: 이메일로 학생 조회
    Optional<Student> findByEmail(String email);



    // 회원가입용: 이메일 중복 체크
    boolean existsByEmail(String email);

    Optional<Student> findByPhoneNumber(String phoneNumber);

    // 특정 학년/반 학생 목록 조회 (관리자용)
    // 예: 1학년 2반 학생들 조회
    // List<Student> findByGradeAndClassNo(Integer grade, Integer classNo);
}