package com.yumyumcoach.domain.diet.service;

import com.yumyumcoach.domain.diet.dto.DietRecordDto;
import com.yumyumcoach.domain.diet.mapper.DietFoodMapper;
import com.yumyumcoach.domain.diet.mapper.DietRecordMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DietRecordService {

    private final DietRecordMapper dietRecordMapper;
    private final DietFoodMapper dietFoodMapper;

    public DietRecordService(
            DietRecordMapper dietRecordMapper,
            DietFoodMapper dietFoodMapper
    ) {
        this.dietRecordMapper = dietRecordMapper;
        this.dietFoodMapper = dietFoodMapper;
    }

    @Transactional(readOnly = true)
    public List<DietRecordDto> getMyDiets(String userId, LocalDate date, int page, int size) {
        int limit = Math.max(size, 1);
        int offset = Math.max(page, 0) * limit;
        return dietRecordMapper.selectDietRecordsByUserAndDate(userId, date, offset, limit);
    }

    @Transactional(readOnly = true)
    public DietRecordDto getMyDietDetail(String userId, Long dietId) {
        return dietRecordMapper.selectDietRecordDetail(dietId, userId);
    }

    @Transactional
    public Long createMyDiet(String userId, com.yumyumcoach.domain.diet.dto.CreateDietRecordRequest request) {
        request.setUserId(userId); // 신뢰할 수 있는 사용자로 덮어쓰기
        dietRecordMapper.insertDietRecord(request);
        Long dietId = request.getId();
        if (dietId == null) {
            throw new IllegalStateException("Failed to generate diet id");
        }
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            dietFoodMapper.insertDietFoods(dietId, request.getItems());
        }
        return dietId;
    }

    @Transactional
    public void deleteMyDiet(String userId, Long dietId) {
        // 먼저 자식 삭제
        dietFoodMapper.deleteDietFoodsByDietId(dietId);
        int deleted = dietRecordMapper.deleteDietRecord(dietId, userId);
        if (deleted == 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Diet not found"
            );
        }
    }

    @Transactional
    public void updateMyDiet(String userId, Long dietId, com.yumyumcoach.domain.diet.dto.CreateDietRecordRequest request) {
        request.setUserId(userId); // 인증 사용자로 강제
        int updated = dietRecordMapper.updateDietRecord(dietId, request);
        if (updated == 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Diet not found"
            );
        }
        // 음식 항목 재구성: 일단 모두 삭제 후 재삽입 (간단 전략)
        dietFoodMapper.deleteDietFoodsByDietId(dietId);
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            dietFoodMapper.insertDietFoods(dietId, request.getItems());
        }
    }
}

