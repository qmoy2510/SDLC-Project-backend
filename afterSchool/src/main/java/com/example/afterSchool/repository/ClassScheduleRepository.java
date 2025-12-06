package com.example.afterSchool.repository;

import com.example.afterSchool.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    // 특정 수업의 시간표 목록 조회
    // Entity에서 OneToMany로 가져올 수도 있지만, 필요시 직접 조회용
    List<ClassSchedule> findByAfterSchoolClassId(Long classId);

    // 특정 수업의 시간표 삭제 (수업 수정 시 기존 시간표 밀어버릴 때 사용)
    void deleteByAfterSchoolClassId(Long classId);
}