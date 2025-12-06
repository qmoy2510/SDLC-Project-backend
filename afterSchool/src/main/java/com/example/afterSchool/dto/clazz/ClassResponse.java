package com.example.afterSchool.dto.clazz;

import com.example.afterSchool.entity.AfterSchoolClass;
import com.example.afterSchool.entity.ClassSchedule;
import com.example.afterSchool.entity.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponse {
    private Long classId;
    private String title;
    private String description;
    private String teacherName;   // 담당 교사 이름
    private String classLocation; // [NEW] 수업 장소
    private Integer capacity;     // 정원
    private Integer currentCount; // [요구사항] 현재 수강 인원
    private List<ScheduleResponse> schedules; // 시간표 정보

    // Entity -> DTO 변환 편의 메서드
    public static ClassResponse from(AfterSchoolClass entity, Integer currentCount) {
        return ClassResponse.builder()
                .classId(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .teacherName(entity.getTeacher().getName())
                .classLocation(entity.getClassLocation())
                .capacity(entity.getCapacity())
                .currentCount(currentCount) // 별도로 조회해서 주입
                .schedules(entity.getSchedules().stream()
                        .map(ScheduleResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ScheduleResponse {
        private DayOfWeek dayOfWeek;
        private String startTime;
        private String endTime;

        public static ScheduleResponse from(ClassSchedule schedule) {
            return ScheduleResponse.builder()
                    .dayOfWeek(schedule.getDayOfWeek())
                    .startTime(schedule.getStartTime())
                    .endTime(schedule.getEndTime())
                    .build();
        }
    }
}