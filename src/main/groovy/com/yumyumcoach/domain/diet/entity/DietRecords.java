package com.yumyumcoach.domain.diet.entity;

// import jakarta.persistence.Column;
// import jakarta.persistence.EntityListeners;
// import jakarta.persistence.MappedSuperclass;
// import java.time.LocalDateTime;
// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
// import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="diet_records")
public class DietRecords {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "email", nullable = false)
  private Accounts email;
  @Column(name = "record_date", nullable = false)
  private LocalDateTime recordDate;
  @Column(name = "meal_type", nullable = false)
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

