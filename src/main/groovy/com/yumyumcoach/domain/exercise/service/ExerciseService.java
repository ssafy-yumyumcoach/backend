package com.yumyumcoach.domain.exercise.service;

import com.yumyumcoach.domain.exercise.dto.DeleteExerciseRecordResponse;
import com.yumyumcoach.domain.exercise.dto.ExerciseRecordRequest;
import com.yumyumcoach.domain.exercise.dto.ExerciseRecordResponse;
import com.yumyumcoach.domain.exercise.dto.ExerciseResponse;
import com.yumyumcoach.domain.exercise.entity.Exercise;
import com.yumyumcoach.domain.exercise.entity.ExerciseRecord;
import com.yumyumcoach.domain.exercise.entity.ExerciseRecordWithExercise;
import com.yumyumcoach.domain.exercise.mapper.ExerciseMapper;
import com.yumyumcoach.domain.exercise.mapper.ExerciseRecordMapper;
import com.yumyumcoach.domain.exercise.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseService {
    private final ExerciseMapper exerciseMapper;
    private final ExerciseRecordMapper exerciseRecordMapper;
    private final ProfileMapper profileMapper;

    public List<ExerciseResponse> getExercises() {
        return exerciseMapper.findAll().stream()
                .map(this::toExerciseResponse)
                .toList();
    }

    public List<ExerciseRecordResponse> getMyExerciseRecords(String email, LocalDate recordDate) {
        return exerciseRecordMapper.findByEmailAndDate(email, recordDate).stream()
                .map(this::toExerciseRecordResponse)
                .toList();
    }

    public ExerciseRecordResponse getMyExerciseRecordDetail(String email, Long recordId) {
        ExerciseRecordWithExercise record = exerciseRecordMapper.findDetailByIdAndEmail(recordId, email);
        if (record == null) {
            throw new IllegalArgumentException("운동 기록을 찾을 수 없습니다. recordId=" + recordId);
        }
        return toExerciseRecordResponse(record);
    }

    @Transactional
    public List<ExerciseRecordResponse> createMyExerciseRecords(String email, List<ExerciseRecordRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("운동 기록 요청 리스트가 비어 있습니다.");
        }

        return requests.stream()
                .map(request -> createExerciseRecord(email, request))
                .toList();
    }

    @Transactional
    public ExerciseRecordResponse updateMyExerciseRecord(String email, Long recordId, ExerciseRecordRequest request) {
        ExerciseRecordWithExercise existing = exerciseRecordMapper.findDetailByIdAndEmail(recordId, email);
        if (existing == null) {
            throw new IllegalArgumentException("운동 기록을 찾을 수 없습니다. recordId=" + recordId);
        }

        double calories = calculateCalories(email, request.getExerciseId(), request.getDurationMinutes());
        LocalDate recordDate = requireRecordDate(request.getRecordDate());

        ExerciseRecord exerciseRecord = ExerciseRecord.builder()
                .id(recordId)
                .email(email)
                .exerciseId(request.getExerciseId())
                .recordDate(recordDate)
                .durationMinutes(request.getDurationMinutes())
                .calories(calories)
                .build();

        exerciseRecordMapper.update(exerciseRecord);
        return getMyExerciseRecordDetail(email, recordId);
    }

    @Transactional
    public DeleteExerciseRecordResponse deleteMyExerciseRecord(String email, Long recordId) {
        ExerciseRecordWithExercise existing = exerciseRecordMapper.findDetailByIdAndEmail(recordId, email);
        if (existing == null) {
            throw new IllegalArgumentException("운동 기록을 찾을 수 없습니다. recordId=" + recordId);
        }
        exerciseRecordMapper.delete(recordId, email);
        return DeleteExerciseRecordResponse.builder()
                .recordId(recordId)
                .deleted(true)
                .deletedAt(LocalDateTime.now())
                .build();
    }

    private ExerciseResponse toExerciseResponse(Exercise exercise) {
        return ExerciseResponse.builder()
                .exerciseId(exercise.getId())
                .name(exercise.getName())
                .met(exercise.getMet())
                .intensityLevel(exercise.getIntensityLevel())
                .type(exercise.getType())
                .description(exercise.getDescription())
                .build();
    }

    private ExerciseRecordResponse toExerciseRecordResponse(ExerciseRecordWithExercise record) {
        return ExerciseRecordResponse.builder()
                .recordId(record.getId())
                .exerciseId(record.getExerciseId())
                .exerciseName(record.getExerciseName())
                .met(record.getMet())
                .intensityLevel(record.getIntensityLevel())
                .type(record.getType())
                .recordDate(record.getRecordDate())
                .durationMinutes(record.getDurationMinutes())
                .calories(record.getCalories())
                .build();
    }

    private double calculateCalories(String email, Long exerciseId, Double durationMinutes) {
        Exercise exercise = exerciseMapper.findById(exerciseId);
        if (exercise == null) {
            throw new IllegalArgumentException("운동 정보를 찾을 수 없습니다. exerciseId=" + exerciseId);
        }

        Double currentWeight = profileMapper.findCurrentWeightByEmail(email);
        if (currentWeight == null) {
            throw new IllegalArgumentException("현재 사용자 프로필의 몸무게 정보를 찾을 수 없습니다. email=" + email);
        }

        double durationHours = durationMinutes / 60.0;
        double rawCalories = exercise.getMet() * currentWeight * durationHours;
        return BigDecimal.valueOf(rawCalories)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private LocalDate requireRecordDate(LocalDate recordDate) {
        if (recordDate == null) {
            throw new IllegalArgumentException("운동 기록 날짜는 필수입니다.");
        }
        return recordDate;
    }

    private ExerciseRecordResponse createExerciseRecord(String email, ExerciseRecordRequest request) {
        double calories = calculateCalories(email, request.getExerciseId(), request.getDurationMinutes());
        LocalDate recordDate = requireRecordDate(request.getRecordDate());

        ExerciseRecord exerciseRecord = ExerciseRecord.builder()
                .email(email)
                .exerciseId(request.getExerciseId())
                .recordDate(recordDate)
                .durationMinutes(request.getDurationMinutes())
                .calories(calories)
                .build();

        exerciseRecordMapper.insert(exerciseRecord);
        return getMyExerciseRecordDetail(email, exerciseRecord.getId());
    }
}
