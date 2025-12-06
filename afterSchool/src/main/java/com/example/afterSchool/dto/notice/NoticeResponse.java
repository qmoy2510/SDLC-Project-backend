package com.example.afterSchool.dto.notice;

import com.example.afterSchool.entity.ClassNotice;
import com.example.afterSchool.entity.enums.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NoticeResponse {
    private Long noticeId;
    private String classTitle;   // 어떤 수업 공지인지
    private String teacherName;  // 작성자
    private String title;
    private String content;
    private NoticeType noticeType; // [NEW] 공지 타입
    private LocalDateTime createdAt;

    public static NoticeResponse from(ClassNotice entity) {
        return NoticeResponse.builder()
                .noticeId(entity.getId())
                .classTitle(entity.getAfterSchoolClass().getTitle())
                .teacherName(entity.getTeacher().getName())
                .title(entity.getTitle())
                .content(entity.getContent())
                .noticeType(entity.getNoticeType())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}