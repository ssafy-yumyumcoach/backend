package com.yumyumcoach.domain.diet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateFoodRequest {

    @NotBlank
    private String name;

    @PositiveOrZero
    private Double carbohydrate;

    @PositiveOrZero
    private Double protein;

    @PositiveOrZero
    private Double fat;

    @PositiveOrZero
    private Double calories;

    public CreateFoodRequest() {
    }

    public CreateFoodRequest(
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

