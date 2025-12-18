package com.yumyumcoach.domain.diet.entity;

import java.time.LocalDateTime;

public class DietRecords {

  private Long id;
  private String email;
  private LocalDateTime recordDate;
  private String mealType;

  protected DietRecords() {}

  public DietRecords(String email, LocalDateTime recordDate, String mealType) {
    this.email = email;
    this.recordDate = recordDate;
    this.mealType = mealType;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
  public LocalDateTime getRecordDate() {
    return recordDate;
  }
  public String getMealType() {
    return mealType;
  }
}

