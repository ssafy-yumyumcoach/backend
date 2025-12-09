package com.yumyumcoach.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 한개 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long postId;

    private Long authorId;
    private String authorUsername;

    private String title;
    private String content;

    private List<String> images;

    private int likeCount;
    private int commentCount;

    private LocalDateTime createdAt;
}
