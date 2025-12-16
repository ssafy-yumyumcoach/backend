package com.yumyumcoach.domain.exercise.controller;

import com.yumyumcoach.domain.exercise.dto.DeleteExerciseRecordResponse;
import com.yumyumcoach.domain.exercise.dto.ExerciseRecordRequest;
import com.yumyumcoach.domain.exercise.dto.ExerciseRecordResponse;
import com.yumyumcoach.domain.exercise.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me/exercise-records")
public class MyExerciseRecordController {
    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseRecordResponse> getMyExerciseRecords(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recordDate
    ) {
        String loginUserEmail = "todo@example.com"; // TODO: 인증 연동 후 교체
        return exerciseService.getMyExerciseRecords(loginUserEmail, recordDate);
    }

    @GetMapping("/{recordId}")
    public ExerciseRecordResponse getMyExerciseRecordDetail(@PathVariable("recordId") Long recordId) {
        String loginUserEmail = "todo@example.com"; // TODO: 인증 연동 후 교체
        return exerciseService.getMyExerciseRecordDetail(loginUserEmail, recordId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ExerciseRecordResponse> createMyExerciseRecord(@RequestBody List<ExerciseRecordRequest> requests) {
        String loginUserEmail = "todo@example.com"; // TODO: 인증 연동 후 교체
        return exerciseService.createMyExerciseRecords(loginUserEmail, requests);
    }

    @PutMapping("/{recordId}")
    public ExerciseRecordResponse updateMyExerciseRecord(@PathVariable("recordId") Long recordId,
                                                         @RequestBody ExerciseRecordRequest request) {
        String loginUserEmail = "todo@example.com"; // TODO: 인증 연동 후 교체
        return exerciseService.updateMyExerciseRecord(loginUserEmail, recordId, request);
    }

    @DeleteMapping("/{recordId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteExerciseRecordResponse deleteMyExerciseRecord(@PathVariable("recordId") Long recordId) {
        String loginUserEmail = "todo@example.com"; // TODO: 인증 연동 후 교체
        return exerciseService.deleteMyExerciseRecord(loginUserEmail, recordId);
    }
}
