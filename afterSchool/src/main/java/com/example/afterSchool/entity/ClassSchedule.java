package com.example.afterSchool.entity;

import com.example.afterSchool.entity.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder // ✅ 추가
@AllArgsConstructor // ✅ 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "class_schedules")
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private AfterSchoolClass afterSchoolClass;

    @Enumerated(EnumType.STRING) // DB에는 'Mon', 'Tue' 문자열로 저장
    @Column(name = "day_of_week", nullable = false, length = 10)
    private DayOfWeek dayOfWeek;

    // DB에 VARCHAR2(5)로 정의했으므로 String 사용 (예: "14:30")
    @Column(name = "start_time", nullable = false, length = 5)
    private String startTime;

    @Column(name = "end_time", nullable = false, length = 5)
    private String endTime;
}