package com.yumyumcoach.domain.diet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateDietFoodRequest {

    private Long foodId; // 선택: 기존 음식 마스터 참조
    private String foodName; // 직접 입력 시 사용

    @NotNull
    @Positive
    private Double serveCount; // 1회 제공량/먹은 양 (serve_count)

    @PositiveOrZero
    private Double calories;

    @PositiveOrZero
    private Double carbs;

    @PositiveOrZero
    private Double protein;

    @PositiveOrZero
    private Double fat;

    @NotNull
    @Positive
    private Integer orderIndex; // 식단 내 표시 순서

    public CreateDietFoodRequest() {
    }

    public CreateDietFoodRequest(
            Long foodId,
            String foodName,
            Double serveCount,
            Double calories,
            Double carbs,
            Double protein,
            Double fat,
            Integer orderIndex
    ) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.serveCount = serveCount;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.orderIndex = orderIndex;
    }

    public Long getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public Double getServeCount() {
        return serveCount;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getCarbs() {
        return carbs;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getFat() {
        return fat;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }
}

