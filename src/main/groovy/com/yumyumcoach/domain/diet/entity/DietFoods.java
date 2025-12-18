package com.yumyumcoach.domain.diet.entity;

public class DietFoods {

  private Long id;

  private DietRecords dietId;
  
  private Integer orderIndex;
  
  private Foods foodId;
  
  private Double weight;
  
  protected DietFoods() {}
  
  public DietFoods(DietRecords dietId, Integer orderIndex, Foods foodId, Double weight) {
    this.dietId = dietId;
    this.orderIndex = orderIndex;
    this.foodId = foodId;
    this.weight = weight;
  }

  public Long getId() {
    return id;
  }
  public DietRecords getDietId() {
    return dietId;
  }
  public Integer getOrderIndex() {
    return orderIndex;
  }
  public Foods getFoodId() {
    return foodId;
  }
  public Double getWeight() {
    return weight;
  }
}