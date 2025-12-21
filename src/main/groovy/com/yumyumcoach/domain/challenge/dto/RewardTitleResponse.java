package com.yumyumcoach.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardTitleResponse {
    private String difficultyCode;        // BEGINNER/INTERMEDIATE/ADVANCED
    private String difficultyName;        // 초급/중급/고급 (join)
    private Integer requiredSuccessDays;  // 목표 일수
    private Double dailyTargetValue;      // 단백질 g 계수 등 (없으면 null)

    private Long titleId;
    private String iconEmoji;
    private String titleName;
    private String titleDescription;
}

