package com.example.afterSchool.service;

import com.example.afterSchool.dto.auth.AuthDto.*;
import com.example.afterSchool.dto.auth.LoginRequest;
import com.example.afterSchool.dto.auth.TokenResponse;
import com.example.afterSchool.entity.Admin;
import com.example.afterSchool.entity.Student;
import com.example.afterSchool.entity.Teacher;
import com.example.afterSchool.repository.AdminRepository;
import com.example.afterSchool.repository.StudentRepository;
import com.example.afterSchool.repository.TeacherRepository;
import com.example.afterSchool.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    // PasswordEncoder 제거됨
    private final JwtTokenProvider jwtTokenProvider;

    // 고정된 인증 코드
    private static final String TEACHER_AUTH_CODE = "TEACHER1234";
    private static final String ADMIN_AUTH_CODE = "ADMIN1234";

    // 1. 학생 회원가입
    @Transactional
    public void signupStudent(StudentSignupRequest req) {
        if (studentRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        Student student = Student.builder()
                .email(req.getEmail())
                .password(req.getPassword()) // [변경] 암호화 없이 그대로 저장
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .grade(req.getGrade())
                .classNo(req.getClassNo())
                .classNumber(req.getClassNumber())
                .build();
        studentRepository.save(student);
    }

    // 2. 교사 회원가입 (인증 코드 검증)
    @Transactional
    public void signupTeacher(TeacherSignupRequest req) {
        if (!TEACHER_AUTH_CODE.equals(req.getAuthCode())) {
            throw new RuntimeException("인증 코드가 일치하지 않습니다.");
        }
        if (teacherRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        Teacher teacher = Teacher.builder()
                .email(req.getEmail())
                .password(req.getPassword()) // [변경] 암호화 없이 그대로 저장
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .build();
        teacherRepository.save(teacher);
    }

    // 3. 관리자 회원가입 (인증 코드 검증)
    @Transactional
    public void signupAdmin(AdminSignupRequest req) {
        if (!ADMIN_AUTH_CODE.equals(req.getAuthCode())) {
            throw new RuntimeException("인증 코드가 일치하지 않습니다.");
        }
        if (adminRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        Admin admin = Admin.builder()
                .email(req.getEmail())
                .password(req.getPassword()) // [변경] 암호화 없이 그대로 저장
                .name(req.getName())
                .build();
        adminRepository.save(admin);
    }

    // 4. 학생 로그인
    public TokenResponse loginStudent(LoginRequest req) {
        Student student = studentRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        // [변경] 문자열 단순 비교
        if (!student.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(student.getEmail(), "STUDENT");
        return new TokenResponse(token);
    }

    // 5. 교사 로그인
    public TokenResponse loginTeacher(LoginRequest req) {
        Teacher teacher = teacherRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        // [변경] 문자열 단순 비교
        if (!teacher.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(teacher.getEmail(), "TEACHER");
        return new TokenResponse(token);
    }

    // 6. 관리자 로그인
    public TokenResponse loginAdmin(LoginRequest req) {
        Admin admin = adminRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        // [변경] 문자열 단순 비교
        if (!admin.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(admin.getEmail(), "ADMIN");
        return new TokenResponse(token);
    }
}