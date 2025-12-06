package com.example.afterSchool.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder // ✅ 이걸 추가해야 .builder() 사용 가능
@AllArgsConstructor // ✅ Builder를 쓰려면 전체 생성자가 필요함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

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