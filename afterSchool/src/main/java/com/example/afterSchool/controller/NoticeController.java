package com.example.afterSchool.controller;

import com.example.afterSchool.dto.notice.NoticeResponse;
import com.example.afterSchool.dto.notice.NoticeSaveRequest;
import com.example.afterSchool.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 1. 공지사항 작성 (교사)
    @PostMapping("/classes/{classId}/notices")
    public ResponseEntity<String> createNotice(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long classId,
            @RequestBody NoticeSaveRequest req) {
        noticeService.createNotice(user.getUsername(), classId, req);
        return ResponseEntity.ok("공지 등록 완료");
    }

    // 2. 공지사항 수정 (교사)
    @PutMapping("/notices/{noticeId}")
    public ResponseEntity<String> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody NoticeSaveRequest req) {
        noticeService.updateNotice(noticeId, req);
        return ResponseEntity.ok("공지 수정 완료");
    }

    // 3. 내 공지사항 조회 (학생 - 수강중인 수업의 공지)
    @GetMapping("/students/me/notices")
    public ResponseEntity<List<NoticeResponse>> getMyNoticesStudent(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(noticeService.getMyNoticesForStudent(user.getUsername()));
    }

    // 4. 내 공지사항 조회 (교사 - 내가 쓴 공지)
    @GetMapping("/teachers/me/notices")
    public ResponseEntity<List<NoticeResponse>> getMyNoticesTeacher(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(noticeService.getMyNoticesForTeacher(user.getUsername()));
    }
    // [추가] 공지사항 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<String> deleteNotice(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long noticeId) {
        noticeService.deleteNotice(user.getUsername(), noticeId);
        return ResponseEntity.ok("공지 삭제 완료");
    }
}