package com.example.afterSchool.entity;

import com.example.afterSchool.entity.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder // ✅ 추가
@AllArgsConstructor // ✅ 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "class_attendance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"enrollment_id", "class_date"})
        })
public class ClassAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private ClassEnrollment classEnrollment;

    @Column(name = "class_date", nullable = false)
    private LocalDate classDate; // 날짜만 저장

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttendanceStatus status; // PRESENT, ABSENT, LATE

    @Column(name = "recorded_at", updatable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    public void prePersist() {
        this.recordedAt = LocalDateTime.now();
    }
}