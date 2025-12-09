package com.yumyumcoach.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 작성/수정 요청 공통 DTO.
 * - POST /api/posts/{postId}/comments
 * - PUT  /api/posts/{postId}/comments/{commentId}
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    /**
     * 댓글 내용
     */
    private String content;
}
