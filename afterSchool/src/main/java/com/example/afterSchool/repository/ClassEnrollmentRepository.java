package com.example.afterSchool.repository;


import com.example.afterSchool.entity.AfterSchoolClass;
import com.example.afterSchool.entity.ClassEnrollment;
import com.example.afterSchool.entity.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, Long> {

    // [학생용] 내가 신청한 수업 목록 조회 (마이페이지)
    List<ClassEnrollment> findByStudentId(Long studentId);

    // [학생용] 특정 상태(신청완료/취소)인 내 수업 목록 조회
    List<ClassEnrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);

    // [교사용] 내 수업을 신청한 학생 목록 조회 (출석부 생성용 등)
    List<ClassEnrollment> findByAfterSchoolClassId(Long classId);

    // [중복방지] 이미 신청한 수업인지 확인
    // (학생 ID + 수업 ID + 상태가 ENROLLED인 데이터가 있는지)
    boolean existsByStudentIdAndAfterSchoolClassIdAndStatus(Long studentId, Long classId, EnrollmentStatus status);

    // 신청 내역 단건 조회 (취소 처리 등을 위해)
    Optional<ClassEnrollment> findByStudentIdAndAfterSchoolClassId(Long studentId, Long classId);

    int countByAfterSchoolClassAndStatus(AfterSchoolClass c, EnrollmentStatus s);
}