package com.yumyumcoach.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 운동 기록 생성/수정 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRecordRequest {
    private Long exerciseId;
    private LocalDate recordDate;
    private Double durationMinutes;
}
