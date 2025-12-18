package com.yumyumcoach.domain.diet.dto;

public class FoodDto {

    private Long id;
    private String name;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private Double calories;

    public FoodDto() {
    }

    public FoodDto(
            Long id,
            String name,
            Double carbohydrate,
            Double protein,
            Double fat,
            Double calories
    ) {
        this.id = id;
        this.name = name;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calories = calories;
    }

    public Long getId() {
        return id;
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

