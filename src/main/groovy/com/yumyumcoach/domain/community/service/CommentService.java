package com.yumyumcoach.domain.community.service;


import com.yumyumcoach.domain.community.dto.CommentRequest;
import com.yumyumcoach.domain.community.dto.CommentResponse;
import com.yumyumcoach.domain.community.dto.GetCommentsResponse;
import com.yumyumcoach.domain.community.entity.Post;
import com.yumyumcoach.domain.community.entity.PostComment;
import com.yumyumcoach.domain.community.mapper.PostCommentMapper;
import com.yumyumcoach.domain.community.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Community 댓글 관련 서비스.
 * - 댓글 목록 조회
 * - 댓글 작성/수정/삭제
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final PostMapper postMapper;
    private final PostCommentMapper postCommentMapper;

    /**
     * 특정 게시글의 댓글 목록 조회
     * - GET /api/posts/{postId}/comments
     */
    public GetCommentsResponse getComments(Long postId) {
        // 1) 게시글 존재 여부 확인
        Post post = postMapper.findById(postId);
        if (post == null) {
            // TODO: 커스텀 예외로 교체
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. postId=" + postId);
        }

        // 2) 댓글 목록 조회
        List<PostComment> comments = postCommentMapper.findByPostId(postId);

        // 3) CommentResponse 리스트로 매핑
        List<CommentResponse> commentResponses = comments.stream()
                .map(comment -> CommentResponse.builder()
                        .commentId(comment.getId())
                        .postId(comment.getPostId())
                        // User 도메인 연동 전 : 일단 author 관련은 null로 세팅
                        .authorId(null)
                        .authorUsername(null)
                        .authorProfileImageUrl(null)
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build()
                )
                .toList();

        return GetCommentsResponse.builder()
                .postId(postId)
                .totalCount(commentResponses.size())
                .comments(commentResponses)
                .build();
    }

    /**
     * 댓글 작성
     * - POST /api/posts/{postId}/comments
     */
    @Transactional
    public CommentResponse createComment(Long loginUserId, Long postId, CommentRequest request) {
        // 1) 게시글 존재 여부 확인
        Post post = postMapper.findById(postId);
        if (post == null) {
            // TODO: 커스텀 예외로 교체
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. postId=" + postId);
        }

        // 2) 로그인 유저 정보 (User/Auth 연동 전이라 더미 값)
        // TODO: loginUserId -> email, authorId 변환 (User/Auth 도메인 연동)
        String authorEmail = "todo@example.com";

        LocalDateTime now = LocalDateTime.now();

        // 3) PostComment 엔티티 생성
        PostComment comment = PostComment.builder()
                .postId(postId)
                .authorEmail(authorEmail)
                .content(request.getContent())
                .createdAt(now)
                .build();

        // 4) DB 저장 (id 자동 증가)
        postCommentMapper.insert(comment); // useGeneratedKeys=true 로 인해 comment.id 세팅됨

        return CommentResponse.builder()
                .commentId(comment.getId())
                .postId(postId)
                .authorId(null)   // TODO: User 도메인 연동 후 세팅
                .authorUsername(null)
                .authorProfileImageUrl(null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    /**
     * 댓글 수정
     * - PUT /api/posts/{postId}/comments/{commentId}
     */
    @Transactional
    public CommentResponse updateComment(Long loginUserId, Long postId, Long commentId, CommentRequest request) {
        // 1) 댓글 조회
        PostComment existing = postCommentMapper.findById(commentId);
        if (existing == null) {
            // TODO: 커스텀 예외로 교체
            throw new IllegalArgumentException("해당 댓글을 찾을 수 없습니다. commentId=" + commentId);
        }

        // 2) postId 일치 여부 확인 (URL, DB 불일치 방지)
        if (!existing.getPostId().equals(postId)) {
            // TODO: 커스텀 예외로 교체
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다. postId=" + postId);
        }

        // 3) 권한 체크 (작성자 == 로그인 유저인지)
        // TODO: loginUserId -> email 변환 후 existing.getAuthorEmail() 과 비교

        // 4) 내용 수정
        LocalDateTime now = LocalDateTime.now();
        PostComment toUpdate = PostComment.builder()
                .id(commentId)
                .content(request.getContent())
                .build();

        postCommentMapper.update(toUpdate);

        return CommentResponse.builder()
                .commentId(commentId)
                .postId(postId)
                .authorId(null)
                .authorUsername(null)
                .authorProfileImageUrl(null)
                .content(request.getContent())
                .createdAt(existing.getCreatedAt())
                .build();
    }

    /**
     * 댓글 삭제
     * - DELETE /api/posts/{postId}/comments/{commentId}
     */
    @Transactional
    public void deleteComment(Long loginUserId, Long postId, Long commentId) {
        // 1) 댓글 조회
        PostComment existing = postCommentMapper.findById(commentId);
        if (existing == null) {
            // TODO: 커스텀 예외로 교체
            return;
        }

        // 2) postId 일치 여부 확인
        if (!existing.getPostId().equals(postId)) {
            // TODO: 커스텀 예외로 교체
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다. postId=" + postId);
        }

        // 3) 권한 체크 (작성자 == 로그인 유저인지)
        // TODO: loginUserId -> email 변환 후 existing.getAuthorEmail() 비교

        // 4) 삭제
        postCommentMapper.deleteByPostId(postId);
    }
}
