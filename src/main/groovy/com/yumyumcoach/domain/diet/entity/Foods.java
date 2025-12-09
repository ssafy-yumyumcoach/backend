package com.yumyumcoach.domain.diet.entity;

import jakarta.persistence.*;
// import java.time.LocalDateTime;

@Entity
@Table(name="foods")
public class Foods {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "carbohydrate", nullable = true)
  private Double carbohydrate;
  @Column(name = "protein", nullable = true)
  private Double protein;
  @Column(name = "fat", nullable = true)
  private Double fat;
  @Column(name = "calories", nullable = true)
  private Double calories;

  protected Foods() {}
  public Foods(String name, Double carbohydrate, Double protein, Double fat, Double calories) {
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