package com.example.afterSchool.repository;

import com.example.afterSchool.entity.ClassAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClassAttendanceRepository extends JpaRepository<ClassAttendance, Long> {

    // [학생용] 특정 수업(수강신청ID)의 내 출석 기록 전체 조회
    List<ClassAttendance> findByClassEnrollmentId(Long enrollmentId);

    // [교사용] 특정 날짜의 출석 기록 조회 (중복 체크나 화면 표시용)
    Optional<ClassAttendance> findByClassEnrollmentIdAndClassDate(Long enrollmentId, LocalDate classDate);

    // 참고: "특정 수업의 오늘 날짜 출석부"를 만들려면
    // Service 계층에서 Enrollment 목록을 가져온 뒤, 이 Repository를 루프 돌거나
    // @Query를 사용하여 Join 쿼리를 짜는 것이 성능상 좋습니다.
}