package com.yumyumcoach.domain.diet.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DietRecordDto {

    private Long id;
    private String userId;
    private LocalDateTime recordDate;
    private String mealType;
    private String title;
    private String memo;

    private Double totalCalories;
    private Double totalCarbs;
    private Double totalProtein;
    private Double totalFat;

    private List<DietFoodDto> items;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DietRecordDto() {
    }

    public DietRecordDto(
            Long id,
            String userId,
            LocalDateTime recordDate,
            String mealType,
            String title,
            String memo,
            Double totalCalories,
            Double totalCarbs,
            Double totalProtein,
            Double totalFat,
            List<DietFoodDto> items,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.recordDate = recordDate;
        this.mealType = mealType;
        this.title = title;
        this.memo = memo;
        this.totalCalories = totalCalories;
        this.totalCarbs = totalCarbs;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getTitle() {
        return title;
    }

    public String getMemo() {
        return memo;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public Double getTotalCarbs() {
        return totalCarbs;
    }

    public Double getTotalProtein() {
        return totalProtein;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public List<DietFoodDto> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

