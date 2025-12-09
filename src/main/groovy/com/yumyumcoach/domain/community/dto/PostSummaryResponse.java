package com.yumyumcoach.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 전체 피드(게시글 목록)에서
 * 카드 하나(게시글 1개)를 표현하기 위한 응답 DTO.
 * - 리스트 화면 전용 요약 정보
 * - 상세 화면에서는 별도의 GetPostDetailResponse 사용 예정
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryResponse {
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
     * 피드에서 보여줄 게시글 내용 요약
     * 글이 길어지면 일부만 잘라서 내려주는 용도로 사용
     */
    private String contentPreview;

    /**
     * 피드 카드 우측에 보여줄 썸네일 이미지 URL
     * - 이미지가 없으면 null 가능
     */
    private String thumbnailUrl;

    /**
     * 좋아요 개수
     */
    private int likeCount;

    /**
     * 댓글 개수
     */
    private int commentCount;

    /**
     * 게시글 작성 시각
     */
    private LocalDateTime createdAt;
}
