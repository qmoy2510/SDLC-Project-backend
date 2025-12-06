package com.example.afterSchool.repository;

import com.example.afterSchool.entity.Admin;
import com.example.afterSchool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // 로그인용: 이메일로 관리자 조회
    Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);
}