package com.example.afterSchool.controller;

import com.example.afterSchool.dto.auth.AuthDto.*;
import com.example.afterSchool.dto.auth.LoginRequest;
import com.example.afterSchool.dto.auth.TokenResponse;
import com.example.afterSchool.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // --- 회원가입 ---
    @PostMapping("/signup/student")
    public ResponseEntity<String> signupStudent(@RequestBody StudentSignupRequest req) {
        authService.signupStudent(req);
        return ResponseEntity.ok("학생 회원가입 성공");
    }

    @PostMapping("/signup/teacher")
    public ResponseEntity<String> signupTeacher(@RequestBody TeacherSignupRequest req) {
        authService.signupTeacher(req);
        return ResponseEntity.ok("교사 회원가입 성공");
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<String> signupAdmin(@RequestBody AdminSignupRequest req) {
        authService.signupAdmin(req);
        return ResponseEntity.ok("관리자 회원가입 성공");
    }

    // --- 로그인 ---
    @PostMapping("/login/student")
    public ResponseEntity<TokenResponse> loginStudent(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.loginStudent(req));
    }

    @PostMapping("/login/teacher")
    public ResponseEntity<TokenResponse> loginTeacher(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.loginTeacher(req));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<TokenResponse> loginAdmin(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.loginAdmin(req));
    }
}