package com.example.afterSchool.dto.clazz;

import com.example.afterSchool.entity.enums.DayOfWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ClassCreateRequest {
    private String title;
    private String description;
    private Integer capacity;
    private String classLocation; // [NEW] 수업 장소
    private List<ScheduleDto> schedules; // 시간표 리스트 (아래 정의)

    @Getter
    @NoArgsConstructor
    public static class ScheduleDto {
        private DayOfWeek dayOfWeek; // MON, TUE...
        private String startTime;    // "14:30"
        private String endTime;      // "15:20"
    }
}