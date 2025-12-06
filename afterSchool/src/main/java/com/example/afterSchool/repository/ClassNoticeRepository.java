package com.example.afterSchool.repository;

import com.example.afterSchool.entity.ClassNotice;
import com.example.afterSchool.entity.enums.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassNoticeRepository extends JpaRepository<ClassNotice, Long> {

    // 기존: 특정 수업의 모든 공지 조회
    List<ClassNotice> findByAfterSchoolClassIdOrderByCreatedAtDesc(Long classId);

    // [NEW] 특정 수업의 특정 타입 공지 조회 (예: '휴강' 공지만 보기)
    List<ClassNotice> findByAfterSchoolClassIdAndNoticeTypeOrderByCreatedAtDesc(Long classId, NoticeType noticeType);
}