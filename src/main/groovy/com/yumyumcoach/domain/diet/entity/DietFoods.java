package com.yumyumcoach.domain.diet.entity;

import jakarta.persistence.*;

@Entity
@Table(name="diet_foods")
public class DietFoods {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "diet_id", nullable = false, unique = true) // 어느 식단에 해당하는 음식인지
  private DietRecords dietId;
  
  @Column(name = "order_index", nullable = false, unique = true) // 식단에 등록되는 음식의 순서
  private Integer orderIndex;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "food_id", nullable = false) // 하나의 식단에 여러 음식이 있을 수 있음
  private Foods foodId;
  
  @Column(name = "weight", nullable = true)
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