package com.example.afterSchool.entity;

import com.example.afterSchool.entity.enums.NoticeType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder // ✅ 추가
@AllArgsConstructor // ✅ 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "class_notices")
public class ClassNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private AfterSchoolClass afterSchoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    // [NEW] 공지 타입 추가 (Oracle에는 VARCHAR로 저장됨)
    @Enumerated(EnumType.STRING)
    @Column(name = "notice_type", nullable = false, length = 20)
    private NoticeType noticeType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        // 기본값 설정이 필요하다면 여기서 처리
        if (this.noticeType == null) {
            this.noticeType = NoticeType.COMMON;
        }
    }
}