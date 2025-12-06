package com.example.afterSchool.repository;

import com.example.afterSchool.entity.AfterSchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AfterSchoolClassRepository extends JpaRepository<AfterSchoolClass, Long> {

    // 기존 메서드들...
    List<AfterSchoolClass> findByTeacherId(Long teacherId);
    List<AfterSchoolClass> findByTitleContaining(String keyword);

    // [NEW] 장소로 수업 검색 (예: '과학실'에서 하는 수업 찾기)
    List<AfterSchoolClass> findByClassLocationContaining(String location);

    List<AfterSchoolClass> findAllByOrderByCreatedAtDesc();
}