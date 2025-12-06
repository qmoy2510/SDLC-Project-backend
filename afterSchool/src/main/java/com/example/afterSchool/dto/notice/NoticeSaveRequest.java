package com.example.afterSchool.dto.notice;

import com.example.afterSchool.entity.enums.NoticeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeSaveRequest {
    private String title;
    private String content;
    private NoticeType noticeType; // [NEW] COMMON, CANCELED, CHANGE
}