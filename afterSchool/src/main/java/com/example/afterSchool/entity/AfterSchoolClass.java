package com.example.afterSchool.entity;

import com.example.afterSchool.dto.clazz.ClassCreateRequest; // DTO import 필요
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "afterschool_classes")
public class AfterSchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "class_location", nullable = false, length = 100)
    private String classLocation;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 1. 시간표 (기존) - orphanRemoval = true 필수 (리스트에서 빼면 DB에서도 삭제됨)
    @Builder.Default
    @OneToMany(mappedBy = "afterSchoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSchedule> schedules = new ArrayList<>();

    // 2. [추가] 수강신청 내역 (수업 삭제 시 같이 삭제됨)
    @Builder.Default
    @OneToMany(mappedBy = "afterSchoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassEnrollment> enrollments = new ArrayList<>();

    // 3. [추가] 공지사항 (수업 삭제 시 같이 삭제됨)
    @Builder.Default
    @OneToMany(mappedBy = "afterSchoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassNotice> notices = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // [기능 추가] 수업 정보 수정 메서드
    public void update(String title, String description, Integer capacity, String classLocation) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.classLocation = classLocation;
    }
}