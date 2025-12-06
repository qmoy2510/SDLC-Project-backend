package com.example.afterSchool.controller;

import com.example.afterSchool.dto.clazz.ClassCreateRequest;
import com.example.afterSchool.dto.clazz.ClassResponse;
import com.example.afterSchool.dto.clazz.ClassUpdateRequest;
import com.example.afterSchool.dto.user.UserDto;
import com.example.afterSchool.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    // 1. 전체 수업 조회
    @GetMapping("/classes")
    public ResponseEntity<List<ClassResponse>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    // 2. 수업 개설 (교사)
    @PostMapping("/classes")
    public ResponseEntity<String> createClass(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ClassCreateRequest req) {
        classService.createClass(user.getUsername(), req);
        return ResponseEntity.ok("수업 개설 완료");
    }

    // 3. 수업 삭제 (교사/관리자)
    @DeleteMapping("/classes/{classId}")
    public ResponseEntity<String> deleteClass(@PathVariable Long classId) {
        classService.deleteClass(classId);
        return ResponseEntity.ok("수업 삭제 완료");
    }

    // 4. 수강 신청 (학생)
    @PostMapping("/classes/{classId}/enroll")
    public ResponseEntity<String> enrollClass(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long classId) {
        classService.enrollClass(user.getUsername(), classId);
        return ResponseEntity.ok("수강신청 완료");
    }

    // 5. 내 수업 조회 (학생)
    @GetMapping("/students/me/classes")
    public ResponseEntity<List<ClassResponse>> getMyClassesStudent(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(classService.getMyClassesForStudent(user.getUsername()));
    }

    // 6. 내 수업 조회 (교사 - 내가 개설한 수업)
    @GetMapping("/teachers/me/classes")
    public ResponseEntity<List<ClassResponse>> getMyClassesTeacher(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(classService.getMyClassesForTeacher(user.getUsername()));
    }

    // 7. 특정 수업의 수강생 리스트 조회 (교사)
    @GetMapping("/classes/{classId}/students")
    public ResponseEntity<List<UserDto.StudentResponse>> getStudentsInClass(@PathVariable Long classId) {
        return ResponseEntity.ok(classService.getStudentsInClass(classId));
    }
    // [추가] 수업 수정
    @PutMapping("/classes/{classId}")
    public ResponseEntity<String> updateClass(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long classId,
            @RequestBody ClassUpdateRequest req) {
        classService.updateClass(user.getUsername(), classId, req);
        return ResponseEntity.ok("수업 수정 완료");
    }

}