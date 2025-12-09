package com.yumyumcoach.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 게시글 작성/수정 공통 요청 DTO.
 * - POST /api/posts
 * - PUT  /api/posts/{postId}
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 본문
     */
    private String content;

    /**
     * 이미지 URL 목록
     */
    private List<String> images;
}

