package com.yumyumcoach.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 조회 응답 DTO.
 * - GET /api/posts/{postId}
 * - 피드에서 카드를 눌렀을 때 나오는 상세 화면용 데이터
 * - 댓글 목록은 별도 API (/api/posts/{postId}/comments) 로 제공
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostDetailResponse {
    /**
     * 게시글 ID
     */
    private Long postId;

    /**
     * 작성자 ID
     * - User 도메인 연동 후 채워질 값
     */
    private Long authorId;

    /**
     * 작성자 닉네임
     */
    private String authorUsername;

    /**
     * 작성자 프로필 이미지 URL
     */
    private String authorProfileImageUrl;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 전체 본문
     */
    private String content;

    /**
     * 게시글에 포함된 이미지 전체 URL 목록
     * - post_images 테이블 기반 (order_index 오름차순 정렬 권장)
     */
    private List<String> images;

    /**
     * 좋아요 개수
     */
    private int likeCount;

    /**
     * 댓글 개수
     */
    private int commentCount;

    /**
     * 현재 사용자가 이 게시글에 좋아요를 눌렀는지 여부
     */
    private boolean isLikedByMe;

    /**
     * 게시글 작성 시각
     */
    private LocalDateTime createdAt;

    /**
     * 마지막 수정 시각
     */
    private LocalDateTime updatedAt;
}
