package com.yumyumcoach.domain.diet.controller;

import com.yumyumcoach.domain.diet.dto.DietRecordDto;
import com.yumyumcoach.domain.diet.service.DietRecordService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/me/diets")
public class DietRecordController {

    private final DietRecordService dietRecordService;

    public DietRecordController(DietRecordService dietRecordService) {
        this.dietRecordService = dietRecordService;
    }

    @GetMapping
    public ResponseEntity<List<DietRecordDto>> getMyDiets(
            Principal principal,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        String userId = principal.getName();
        return ResponseEntity.ok(dietRecordService.getMyDiets(userId, date, page, size));
    }

    @GetMapping("/{dietId}")
    public ResponseEntity<DietRecordDto> getMyDietDetail(
            Principal principal,
            @PathVariable Long dietId
    ) {
        String userId = principal.getName();
        DietRecordDto dto = dietRecordService.getMyDietDetail(userId, dietId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Long> createMyDiet(
            Principal principal,
            @Valid @RequestBody com.yumyumcoach.domain.diet.dto.CreateDietRecordRequest request
    ) {
        String userId = principal.getName();
        Long dietId = dietRecordService.createMyDiet(userId, request);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(dietId);
    }

    @DeleteMapping("/{dietId}")
    public ResponseEntity<Void> deleteMyDiet(
            Principal principal,
            @PathVariable Long dietId
    ) {
        String userId = principal.getName();
        dietRecordService.deleteMyDiet(userId, dietId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{dietId}")
    public ResponseEntity<Void> updateMyDiet(
            Principal principal,
            @PathVariable Long dietId,
            @Valid @RequestBody com.yumyumcoach.domain.diet.dto.CreateDietRecordRequest request
    ) {
        String userId = principal.getName();
        dietRecordService.updateMyDiet(userId, dietId, request);
        return ResponseEntity.ok().build();
    }
}

