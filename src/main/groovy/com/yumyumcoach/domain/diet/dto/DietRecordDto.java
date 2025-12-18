package com.yumyumcoach.domain.diet.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DietRecordDto {

    private Long id;
    private String userId;
    private LocalDateTime recordDate;
    private String mealType;
    private List<DietFoodDto> items;

    public DietRecordDto() {
    }

    public DietRecordDto(
            Long id,
            String userId,
            LocalDateTime recordDate,
            String mealType,
            List<DietFoodDto> items
    ) {
        this.id = id;
        this.userId = userId;
        this.recordDate = recordDate;
        this.mealType = mealType;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public String getMealType() {
        return mealType;
    }

    public List<DietFoodDto> getItems() {
        return items;
    }
}

