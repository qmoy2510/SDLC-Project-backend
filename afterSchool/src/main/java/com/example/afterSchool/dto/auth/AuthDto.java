package com.example.afterSchool.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    public static class StudentSignupRequest {
        private String email;
        private String password;
        private String name;
        private String phoneNumber; // [NEW] 연락처
        private Integer grade;
        private Integer classNo;
        private Integer classNumber;
    }

    @Getter
    @NoArgsConstructor
    public static class TeacherSignupRequest {
        private String email;
        private String password;
        private String name;
        private String phoneNumber; // [NEW] 연락처
        private String authCode;    // [인증] 서버의 고정 코드와 비교
    }

    @Getter
    @NoArgsConstructor
    public static class AdminSignupRequest {
        private String email;
        private String password;
        private String name;
        private String authCode;    // [인증] 서버의 고정 코드와 비교
    }
}