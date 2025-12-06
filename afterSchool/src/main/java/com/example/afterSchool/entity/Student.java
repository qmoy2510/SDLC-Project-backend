package com.example.afterSchool.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder // ✅ 추가
@AllArgsConstructor // ✅ 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer grade;

    @Column(name = "class_no", nullable = false)
    private Integer classNo;

    @Column(name = "class_number", nullable = false)
    private Integer classNumber;

    // [NEW] 휴대전화 번호 추가
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}