package com.example.afterSchool.dto.user;

import com.example.afterSchool.entity.Admin;
import com.example.afterSchool.entity.Student;
import com.example.afterSchool.entity.Teacher;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @Builder
    public static class StudentResponse {
        private Long studentId;
        private String email;
        private String name;
        private Integer grade;
        private Integer classNo;
        private Integer classNumber;
        private String phoneNumber;

        public static StudentResponse from(Student student) {
            return StudentResponse.builder()
                    .studentId(student.getId())
                    .email(student.getEmail())
                    .name(student.getName())
                    .grade(student.getGrade())
                    .classNo(student.getClassNo())
                    .classNumber(student.getClassNumber())
                    .phoneNumber(student.getPhoneNumber())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class TeacherResponse {
        private Long teacherId;
        private String email;
        private String name;
        private String phoneNumber;

        public static TeacherResponse from(Teacher teacher) {
            return TeacherResponse.builder()
                    .teacherId(teacher.getId())
                    .email(teacher.getEmail())
                    .name(teacher.getName())
                    .phoneNumber(teacher.getPhoneNumber())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class AdminResponse {
        private Long adminId;
        private String email;
        private String name;

        public static AdminResponse from(Admin admin) {
            return AdminResponse.builder()
                    .adminId(admin.getId())
                    .email(admin.getEmail())
                    .name(admin.getName())
                    .build();
        }
    }
}