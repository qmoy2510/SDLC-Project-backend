package com.example.afterSchool.service;

import com.example.afterSchool.dto.notice.NoticeResponse;
import com.example.afterSchool.dto.notice.NoticeSaveRequest;
import com.example.afterSchool.entity.*;
import com.example.afterSchool.entity.enums.EnrollmentStatus;
import com.example.afterSchool.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final ClassNoticeRepository noticeRepository;
    private final AfterSchoolClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ClassEnrollmentRepository enrollmentRepository;

    // 1. 공지사항 작성
    @Transactional
    public void createNotice(String teacherEmail, Long classId, NoticeSaveRequest req) {
        Teacher teacher = teacherRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new RuntimeException("교사 정보 없음"));
        AfterSchoolClass clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("수업 정보 없음"));

        // 교사가 해당 수업의 담당자인지 체크 (권장)
        if (!clazz.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("본인의 수업에만 공지를 등록할 수 있습니다.");
        }

        ClassNotice notice = ClassNotice.builder()
                .teacher(teacher)
                .afterSchoolClass(clazz)
                .title(req.getTitle())
                .content(req.getContent())
                .noticeType(req.getNoticeType())
                .build();

        noticeRepository.save(notice);
    }

    // 2. 공지사항 수정
    @Transactional
    public void updateNotice(Long noticeId, NoticeSaveRequest req) {
        ClassNotice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공지입니다."));

        // Entity에 수정 메서드가 없다면 Setter 사용 불가(Builder패턴이라 Setter 없음)
        // JPA의 Dirty Checking을 위해 Entity에 메서드 추가가 필요하지만,
        // 여기서는 Entity 수정을 못하므로 부득이하게 Repository에서 직접 Update 쿼리를 날리거나
        // Entity에 `public void update(...)` 메서드가 있다고 "가정"하고 작성합니다.

        // *주의: Entity에 아래 메서드를 꼭 추가해주세요.*
        // notice.update(req.getTitle(), req.getContent(), req.getNoticeType());
    }

    // 3. 학생용 공지 조회 (내가 듣는 수업들의 공지 모아보기)
    public List<NoticeResponse> getMyNoticesForStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow();

        // 수강중인 수업 ID 목록 추출
        List<Long> myClassIds = enrollmentRepository.findByStudentIdAndStatus(student.getId(), EnrollmentStatus.ENROLLED)
                .stream()
                .map(e -> e.getAfterSchoolClass().getId())
                .collect(Collectors.toList());

        // 해당 수업들의 모든 공지 조회 후 최신순 정렬
        // (성능상 IN 절 쿼리가 좋지만 여기선 반복문/스트림으로 처리)
        return myClassIds.stream()
                .flatMap(classId -> noticeRepository.findByAfterSchoolClassIdOrderByCreatedAtDesc(classId).stream())
                .sorted(Comparator.comparing(ClassNotice::getCreatedAt).reversed())
                .map(NoticeResponse::from)
                .collect(Collectors.toList());
    }

    // 4. 교사용 공지 조회 (내가 쓴 공지)
    public List<NoticeResponse> getMyNoticesForTeacher(String email) {
        Teacher teacher = teacherRepository.findByEmail(email).orElseThrow();

        // NoticeRepository에 findByTeacherId가 없으므로 전체에서 필터링하거나 JPQL 추가 필요
        // 여기선 전체 조회 후 스트림 필터링 사용 (데이터 많을 시 비효율적, 기능 구현 우선)
        return noticeRepository.findAll().stream()
                .filter(n -> n.getTeacher().getId().equals(teacher.getId()))
                .sorted(Comparator.comparing(ClassNotice::getCreatedAt).reversed())
                .map(NoticeResponse::from)
                .collect(Collectors.toList());
    }
    // NoticeService.java 내부에 추가

    // [기능 추가] 공지사항 삭제
    @Transactional
    public void deleteNotice(String teacherEmail, Long noticeId) {
        Teacher teacher = teacherRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new RuntimeException("교사 정보 없음"));

        ClassNotice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("공지 정보 없음"));

        // 작성자 본인인지 확인
        if (!notice.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("본인이 작성한 공지만 삭제할 수 있습니다.");
        }

        noticeRepository.delete(notice);
    }
}