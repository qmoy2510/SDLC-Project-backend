package com.example.afterSchool.entity;

import com.example.afterSchool.entity.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder // ✅ 추가
@AllArgsConstructor // ✅ 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "class_enrollments")
public class ClassEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private AfterSchoolClass afterSchoolClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentStatus status; // ENROLLED, CANCELLED

    @Column(name = "applied_at", updatable = false)
    private LocalDateTime appliedAt;

    @PrePersist
    public void prePersist() {
        this.appliedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = EnrollmentStatus.ENROLLED;
        }
    }
}