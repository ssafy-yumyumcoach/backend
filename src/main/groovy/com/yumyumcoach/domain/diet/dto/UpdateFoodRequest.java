package com.yumyumcoach.domain.diet.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class UpdateFoodRequest {

    private String name;

    @PositiveOrZero
    private Double carbohydrate;

    @PositiveOrZero
    private Double protein;

    @PositiveOrZero
    private Double fat;

    @PositiveOrZero
    private Double calories;

    public UpdateFoodRequest() {
    }

    public UpdateFoodRequest(
            String name,
            Double carbohydrate,
            Double protein,
            Double fat,
            Double calories
    ) {
        this.name = name;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getFat() {
        return fat;
    }

    public Double getCalories() {
        return calories;
    }
}

