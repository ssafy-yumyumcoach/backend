package com.yumyumcoach.domain.diet.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class CreateDietRecordRequest {

    private Long id; // insert 시 생성된 PK를 MyBatis가 채우기 위함

    @NotBlank
    private String userId; // accounts.email 참조

    @NotNull
    private LocalDateTime recordDate;

    @NotBlank
    private String mealType; // 예: breakfast/lunch/dinner/snack

    @NotEmpty
    @Valid
    private List<CreateDietFoodRequest> items;

    public CreateDietRecordRequest() {
    }

    public CreateDietRecordRequest(
            String userId,
            LocalDateTime recordDate,
            String mealType,
            List<CreateDietFoodRequest> items
    ) {
        this.userId = userId;
        this.recordDate = recordDate;
        this.mealType = mealType;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    // MyBatis generated keys 채우기용
    public void setId(Long id) {
        this.id = id;
    }

    // 인증 사용자 매핑용
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public String getMealType() {
        return mealType;
    }

    public List<CreateDietFoodRequest> getItems() {
        return items;
    }
}

