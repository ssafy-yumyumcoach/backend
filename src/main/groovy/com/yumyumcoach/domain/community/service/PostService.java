package com.yumyumcoach.domain.community.service;

import com.yumyumcoach.domain.community.dto.GetPostsRequest;
import com.yumyumcoach.domain.community.dto.GetPostsResponse;
import com.yumyumcoach.domain.community.dto.PostRequest;
import com.yumyumcoach.domain.community.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Community 게시글 관련 서비스.
 * - 목록 조회
 * - 상세 조회
 * - 작성/수정/삭제
 * - 좋아요/좋아요 취소
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
//    private final PostMapper postMapper;
//    private final PostImageMapper postImageMapper;
//    private final PostLikeMapper postLikeMapper;
//    private final PostCommentMapper postCommentMapper;

    /**
     * 전체 게시글 목록(피드) 조회
     * - GET /api/posts
     */
    public GetPostsResponse getPosts(GetPostsRequest request, Long loginUserId) {
        // TODO:
        // 1) request.getPage(), request.getSize()를 사용해 페이징 조회
        // 2) Post + 이미지/댓글/좋아요 개수를 조합하여 List<PostResponse> 생성
        // 3) GetPostsResponse에 page/size/totalCount/posts 세팅
        return null;
    }

    /**
     * 게시글 상세 조회
     * - GET /api/posts/{postId}
     */
    public PostResponse getPost(Long postId, Long loginUserId) {
        // TODO:
        // 1) postId로 게시글 조회 (없으면 예외 발생)
        // 2) 이미지 목록, 댓글 수, 좋아요 수, isLikedByMe(post_likes 기반) 조회
        // 3) PostResponse로 매핑해 반환
        return null;
    }

    /**
     * 새 게시글 작성
     * - POST /api/posts
     */
    @Transactional
    public PostResponse createPost(Long loginUserId, PostRequest request) {
        // TODO:
        // 1) 로그인 유저 정보(loginUserId / email) 기준으로 Post 엔티티 생성/저장
        // 2) request.getImages() 기반으로 PostImage 엔티티들 생성/저장
        // 3) 저장된 결과를 PostResponse로 매핑해 반환
        return null;
    }

    /**
     * 게시글 수정
     * - PUT /api/posts/{postId}
     */
    @Transactional
    public PostResponse updatePost(Long loginUserId, Long postId, PostRequest request) {
        // TODO:
        // 1) postId로 게시글 조회 후, 작성자(loginUserId) 권한 체크
        // 2) title, content 수정
        // 3) 이미지 목록 재구성 (기존 PostImage 제거 후 새로 저장 등)
        // 4) 수정 결과를 PostResponse로 매핑해 반환
        return null;
    }

    /**
     * 게시글 삭제
     * - DELETE /api/posts/{postId}
     */
    @Transactional
    public void deletePost(Long loginUserId, Long postId) {
        // TODO:
        // 1) postId로 게시글 조회 후, 작성자 권한 체크
        // 2) 관련 이미지, 댓글, 좋아요 등 연관 데이터 정리
        // 3) 게시글 삭제
    }

    /**
     * 게시글 좋아요 추가
     * - POST /api/posts/{postId}/like
     */
    @Transactional
    public void likePost(Long loginUserId, Long postId) {
        // TODO:
        // 1) 이미 해당 유저가 좋아요 눌렀는지 확인 (post_likes exists 여부)
        // 2) 없으면 PostLike 생성 + Post.likes 컬럼 증가
    }

    /**
     * 게시글 좋아요 취소
     * - DELETE /api/posts/{postId}/like
     */
    @Transactional
    public void unlikePost(Long loginUserId, Long postId) {
        // TODO:
        // 1) post_likes에서 (postId, user) 레코드 삭제
        // 2) Post.likes 컬럼 감소
    }
}
