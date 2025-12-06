package com.example.afterSchool.dto.clazz;

import com.example.afterSchool.entity.enums.DayOfWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClassUpdateRequest {
    private String title;
    private String description;
    private Integer capacity;
    private String classLocation;
    private List<ScheduleDto> schedules; // 수정할 시간표 리스트

    @Getter
    @NoArgsConstructor
    public static class ScheduleDto {
        private DayOfWeek dayOfWeek;
        private String startTime;
        private String endTime;
    }
}