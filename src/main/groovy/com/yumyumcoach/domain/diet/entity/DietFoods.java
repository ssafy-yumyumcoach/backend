package com.yumyumcoach.domain.diet.entity;

import jakarta.persistence.*;

@Entity
@Table(name="diet_foods")
public class DietFoods {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "diet_id", nullable = false, unique = true)
  private DietRecords dietId;
  @Column(name = "order_index", nullable = false, unique = true)
  private Integer orderIndex;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "food_id", nullable = false)
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