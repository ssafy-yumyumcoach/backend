package com.yumyumcoach.domain.community.service;


import com.yumyumcoach.domain.community.dto.CommentRequest;
import com.yumyumcoach.domain.community.dto.CommentResponse;
import com.yumyumcoach.domain.community.dto.GetCommentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Community 댓글 관련 서비스.
 * - 댓글 목록 조회
 * - 댓글 작성/수정/삭제
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    //    private final PostRepository postRepository;
    //    private final PostCommentRepository postCommentRepository;

    /**
     * 특정 게시글의 댓글 목록 조회
     * - GET /api/posts/{postId}/comments
     */
    public GetCommentsResponse getComments(Long postId) {
        // TODO:
        // 1) postId 유효성 확인 (게시글 존재 여부 체크)
        // 2) post_comments에서 해당 postId의 댓글 목록 조회
        // 3) List<CommentResponse>로 매핑 후 GetCommentsResponse로 래핑
        return null;
    }

    /**
     * 댓글 작성
     * - POST /api/posts/{postId}/comments
     */
    @Transactional
    public CommentResponse createComment(Long loginUserId, Long postId, CommentRequest request) {
        // TODO:
        // 1) 게시글 존재 여부 확인
        // 2) 로그인 유저 정보 기반으로 PostComment 엔티티 생성/저장
        // 3) CommentResponse로 매핑해 반환
        return null;
    }

    /**
     * 댓글 수정
     * - PUT /api/posts/{postId}/comments/{commentId}
     */
    @Transactional
    public CommentResponse updateComment(Long loginUserId, Long postId, Long commentId, CommentRequest request) {
        // TODO:
        // 1) commentId로 댓글 조회 + 작성자(loginUserId) 권한 체크
        // 2) content 수정
        // 3) 수정 결과를 CommentResponse로 매핑해 반환
        return null;
    }

    /**
     * 댓글 삭제
     * - DELETE /api/posts/{postId}/comments/{commentId}
     */
    @Transactional
    public void deleteComment(Long loginUserId, Long postId, Long commentId) {
        // TODO:
        // 1) commentId로 댓글 조회 + 작성자 권한 체크
        // 2) 삭제 처리
    }
}
