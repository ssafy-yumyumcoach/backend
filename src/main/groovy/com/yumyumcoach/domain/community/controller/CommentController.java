package com.yumyumcoach.domain.community.controller;

import com.yumyumcoach.domain.community.dto.CommentRequest;
import com.yumyumcoach.domain.community.dto.CommentResponse;
import com.yumyumcoach.domain.community.dto.GetCommentsResponse;
import com.yumyumcoach.domain.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Community 댓글 관련 컨트롤러.
 * - /api/posts/{postId}/comments 하위 엔드포인트 담당
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 특정 게시글의 댓글 목록 조회
    @GetMapping
    public GetCommentsResponse getComments(@PathVariable("postId") Long postId) {
        return commentService.getComments(postId);
    }

    // 댓글 작성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@PathVariable("postId") Long postId,
                                         @RequestBody CommentRequest request) {
        Long loginUserId = 1L; // TODO: 인증 연동 후 실제 사용자 ID 주입
        return commentService.createComment(loginUserId, postId, request);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable("postId") Long postId,
                                         @PathVariable("commentId") Long commentId,
                                         @RequestBody CommentRequest request) {
        Long loginUserId = 1L; // TODO
        return commentService.updateComment(loginUserId, postId, commentId, request);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId) {
        Long loginUserId = 1L; // TODO
        commentService.deleteComment(loginUserId, postId, commentId);
    }
}

