package com.example.afterSchool.controller;

import com.example.afterSchool.dto.user.UserDto;
import com.example.afterSchool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1. 전체 학생 리스트 조회
    @GetMapping("/students")
    public ResponseEntity<List<UserDto.StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    // 2. 전체 교사 리스트 조회
    @GetMapping("/teachers")
    public ResponseEntity<List<UserDto.TeacherResponse>> getAllTeachers() {
        return ResponseEntity.ok(userService.getAllTeachers());
    }

    // 3. 전체 관리자 리스트 조회
    @GetMapping("/admins")
    public ResponseEntity<List<UserDto.AdminResponse>> getAllAdmins() {
        return ResponseEntity.ok(userService.getAllAdmins());
    }

    // [추가] 학생 삭제 (관리자)
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        userService.deleteStudent(studentId);
        return ResponseEntity.ok("학생 삭제 완료");
    }
}