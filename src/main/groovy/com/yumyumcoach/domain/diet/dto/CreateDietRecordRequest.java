package com.yumyumcoach.domain.diet.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class CreateDietRecordRequest {

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

