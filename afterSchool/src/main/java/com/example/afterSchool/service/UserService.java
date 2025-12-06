package com.example.afterSchool.service;

import com.example.afterSchool.dto.user.UserDto;
import com.example.afterSchool.repository.AdminRepository;
import com.example.afterSchool.repository.ClassEnrollmentRepository;
import com.example.afterSchool.repository.StudentRepository;
import com.example.afterSchool.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

    public List<UserDto.StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(UserDto.StudentResponse::from)
                .collect(Collectors.toList());
    }

    public List<UserDto.TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(UserDto.TeacherResponse::from)
                .collect(Collectors.toList());
    }

    public List<UserDto.AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(UserDto.AdminResponse::from)
                .collect(Collectors.toList());
    }
    // UserService.java 내부에 추가
// (StudentEntity에도 @OneToMany(mappedBy="student", cascade=ALL) enrollment가 있으면 좋지만,
// 없다면 여기서 수동으로 지워줘야 할 수도 있습니다. 일단 JPA 관계가 맺어져 있다면 삭제 시도 시 에러가 날 수 있으니
// Student Entity에도 Cascade 설정을 해주거나, 아래처럼 수동 삭제 로직을 넣습니다.)

    private final ClassEnrollmentRepository enrollmentRepository; // 필드 추가 필요

    // [기능 추가] 학생 삭제 (관리자용)
    @Transactional
    public void deleteStudent(Long studentId) {
        // 1. 학생이 수강신청한 내역 먼저 삭제 (FK 에러 방지)
        // (만약 Student Entity에 cascade 설정을 했다면 이 과정 생략 가능)
        enrollmentRepository.deleteAll(enrollmentRepository.findByStudentId(studentId));

        // 2. 학생 삭제
        studentRepository.deleteById(studentId);
    }
}