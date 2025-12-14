package com.yumyumcoach.domain.diet.dto;

public class DietFoodDto {

    private Long id;
    private Long dietId;
    private Long foodId;
    private String foodName;
    private Double serveCount;
    private Double calories;
    private Double carbs;
    private Double protein;
    private Double fat;
    private Integer orderIndex;

    public DietFoodDto() {
    }

    public DietFoodDto(
            Long id,
            Long dietId,
            Long foodId,
            String foodName,
            Double serveCount,
            Double calories,
            Double carbs,
            Double protein,
            Double fat,
            Integer orderIndex
    ) {
        this.id = id;
        this.dietId = dietId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.serveCount = serveCount;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public Long getDietId() {
        return dietId;
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

