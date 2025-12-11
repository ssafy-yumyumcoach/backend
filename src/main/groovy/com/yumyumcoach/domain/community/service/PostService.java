package com.yumyumcoach.domain.community.service;

import com.yumyumcoach.domain.community.dto.GetPostsRequest;
import com.yumyumcoach.domain.community.dto.GetPostsResponse;
import com.yumyumcoach.domain.community.dto.PostRequest;
import com.yumyumcoach.domain.community.dto.PostResponse;
import com.yumyumcoach.domain.community.entity.Post;
import com.yumyumcoach.domain.community.entity.PostImage;
import com.yumyumcoach.domain.community.entity.PostLike;
import com.yumyumcoach.domain.community.mapper.PostCommentMapper;
import com.yumyumcoach.domain.community.mapper.PostImageMapper;
import com.yumyumcoach.domain.community.mapper.PostLikeMapper;
import com.yumyumcoach.domain.community.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private final PostMapper postMapper;
    private final PostImageMapper postImageMapper;
    private final PostLikeMapper postLikeMapper;
    private final PostCommentMapper postCommentMapper;

    /**
     * 전체 게시글 목록(피드) 조회
     * - GET /api/posts
     */
    public GetPostsResponse getPosts(GetPostsRequest request, Long loginUserId) {
        // TODO:
        // 1) request.getPage(), request.getSize()를 사용해 페이징 조회
        int page = request.getPage();
        int size = request.getSize();
        int offset = (page - 1) * size;

        String keyword = request.getKeyword();
        String sort = request.getSort();

        // 2) 게시글 목록 조회
        List<Post> posts = postMapper.findPosts(offset, size, keyword, sort);
        if (posts.isEmpty()) {
            return GetPostsResponse.builder()
                    .page(page)
                    .size(size)
                    .totalCount(0L)
                    .posts(Collections.emptyList())
                    .build();
        }

        // TODO: loginUserId -> email (User/Auth 도메인 연동 후)
        String loginUserEmail = (loginUserId != null) ? "todo@example.com" : null;

        // 3) Post -> PostResponse 매핑
        List<PostResponse> postResponses = posts.stream()
                .map(post -> {
                    Long postId = post.getId();
                    // 이미지 목록
                    List<PostImage> postImages = postImageMapper.findByPostId(postId);
                    List<String> imageUrls = postImages.stream()
                            .map(PostImage::getImageUrl)
                            .toList();
                    // 댓글 개수
                    long commentCount = postCommentMapper.countByPostId(postId);
                    // 좋아요 개수 (posts.likes)
                    int likeCount = post.getLikes();
                    // 내가 좋아요 눌렀는지 여부
                    boolean isLikedByMe = false;
                    if (loginUserEmail != null) {
                        isLikedByMe = postLikeMapper.existsByPostIdAndAuthorEmail(postId, loginUserEmail);
                    }

                    return PostResponse.builder()
                            .postId(postId)
                            // User 도메인 연동 전 : 일단 author 관련은 null로 세팅
                            .authorId(null)
                            .authorUsername(null)
                            .authorProfileImageUrl(null)
                            .title(post.getTitle())
                            .content(post.getContent())
                            .images(imageUrls)
                            .likeCount(likeCount)
                            .commentCount((int) commentCount)
                            .isLikedByMe(isLikedByMe)
                            .createdAt(post.getCreatedAt())
                            .updatedAt(null)
                            .build();
                }).toList();

        // 4) 전체 개수 조회
        long totalCount = postMapper.countPosts(keyword);
        return GetPostsResponse.builder()
                .page(page)
                .size(size)
                .totalCount(totalCount)
                .posts(postResponses)
                .build();
    }

    /**
     * 게시글 상세 조회
     * - GET /api/posts/{postId}
     */
    public PostResponse getPost(Long postId, Long loginUserId) {
        // 1) 게시글 조회
        Post post = postMapper.findById(postId);
        if (post == null) {
            // TODO: 커스텀 예외(PostNotFoundException)로 교체
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. postId=" + postId);
        }

        // 2) 이미지 목록 조회
        List<PostImage> postImages = postImageMapper.findByPostId(postId);
        List<String> imageUrls = postImages.stream()
                .map(PostImage::getImageUrl)
                .toList();

        // 3) 댓글 개수 조회
        long commentCount = postCommentMapper.countByPostId(postId);

        // 4) 좋아요 개수 (posts.likes)
        int likeCount = post.getLikes();

        // 5) 현재 유저가 좋아요 눌렀는지 여부
        boolean isLikedByMe = false;
        if (loginUserId != null) {
            // TODO: loginUserId -> email 변환 (User/Auth 도메인 연동 후 수정)
            String loginUserEmail = "todo@example.com";
            isLikedByMe = postLikeMapper.existsByPostIdAndAuthorEmail(postId, loginUserEmail);
        }

        return PostResponse.builder()
                .postId(post.getId())
                .authorId(null)                 // TODO: User 도메인 연동 후 세팅
                .authorUsername(null)
                .authorProfileImageUrl(null)
                .title(post.getTitle())
                .content(post.getContent())
                .images(imageUrls)
                .likeCount(likeCount)
                .commentCount((int) commentCount)
                .isLikedByMe(isLikedByMe)
                .createdAt(post.getCreatedAt())
                .updatedAt(null)
                .build();
    }

    /**
     * 새 게시글 작성
     * - POST /api/posts
     */
    @Transactional
    public PostResponse createPost(Long loginUserId, PostRequest request) {
        // TODO: loginUserId -> email, authorId 변환 (User/Auth 도메인 연동)
        String authorEmail = "todo@example.com";

        // 1) Post 엔티티 생성
        Post post = Post.builder()
                .authorEmail(authorEmail)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .likes(0)
                .build();

        // 2) posts 테이블 INSERT (id 자동 생성)
        postMapper.insert(post); // useGeneratedKeys="true" 로 인해 post.id 세팅됨
        Long postId = post.getId();

        // 3) 이미지가 있다면 post_images에 INSERT
        List<String> images = request.getImages() != null ? request.getImages() : Collections.emptyList();
        int orderIndex = 1;
        for (String imageUrl : images) {
            PostImage postImage = PostImage.builder()
                    .postId(postId)
                    .imageUrl(imageUrl)
                    .orderIndex(orderIndex++)
                    .build();
            postImageMapper.insert(postImage);
        }

        return PostResponse.builder()
                .postId(postId)
                .authorId(null)               // TODO: User 도메인 연동 후 세팅
                .authorUsername(null)
                .authorProfileImageUrl(null)
                .title(post.getTitle())
                .content(post.getContent())
                .images(images)
                .likeCount(0)
                .commentCount(0)
                .isLikedByMe(false)
                .createdAt(post.getCreatedAt())
                .updatedAt(null)
                .build();
    }

    /**
     * 게시글 수정
     * - PUT /api/posts/{postId}
     */
    @Transactional
    public PostResponse updatePost(Long loginUserId, Long postId, PostRequest request) {
        // 1) 기존 게시글 조회
        Post existing = postMapper.findById(postId);
        if (existing == null) {
            // TODO: 커스텀 예외
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. postId=" + postId);
        }

        // TODO: loginUserId -> email 변환 후, 작성자(authorEmail)와 동일한지 체크

        // 2) 제목/내용 수정
        Post post = Post.builder()
                .id(postId)
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        postMapper.update(post);

        // 3) 이미지 -> 기존 이미지 삭제 후 새로 저장
        postImageMapper.deleteByPostId(postId);
        List<String> images = request.getImages() != null ? request.getImages() : Collections.emptyList();
        int orderIndex = 1;
        for (String imageUrl : images) {
            PostImage postImage = PostImage.builder()
                    .postId(postId)
                    .imageUrl(imageUrl)
                    .orderIndex(orderIndex++)
                    .build();
            postImageMapper.insert(postImage);
        }

        // 4) 댓글 수, 좋아요 수, isLikedByMe 다시 조회
        long commentCount = postCommentMapper.countByPostId(postId);
        int likeCount = post.getLikes();
        boolean isLikedByMe = false;
        if (loginUserId != null) {
            String loginUserEmail = "todo@example.com"; // TODO
            isLikedByMe =  postLikeMapper.existsByPostIdAndAuthorEmail(postId, loginUserEmail);
        }

        return PostResponse.builder()
                .postId(postId)
                .authorId(null)
                .authorUsername(null)
                .authorProfileImageUrl(null)
                .title(request.getTitle())
                .content(request.getContent())
                .images(images)
                .likeCount(likeCount)
                .commentCount((int) commentCount)
                .isLikedByMe(isLikedByMe)
                .createdAt(existing.getCreatedAt())
                .updatedAt(null)
                .build();
    }

    /**
     * 게시글 삭제
     * - DELETE /api/posts/{postId}
     */
    @Transactional
    public void deletePost(Long loginUserId, Long postId) {
        Post existing = postMapper.findById(postId);
        if (existing == null) {
            // TODO : 커스텀 예외
            return;
        }

        // TODO: loginUserId -> email 변환 후, 작성자(authorEmail)와 동일한지 권한 체크

        // 1) 연관 데이터 삭제
        postImageMapper.deleteByPostId(postId);
        postCommentMapper.deleteByPostId(postId);
        // TODO: post_likes 전체 삭제용 메서드(PostLikeMapper.deleteByPostId) 추가해서 호출하면 더 깔끔함

        // 2) 게시글 삭제
        postMapper.delete(postId);
    }

    /**
     * 게시글 좋아요 추가
     * - POST /api/posts/{postId}/like
     */
    @Transactional
    public void likePost(Long loginUserId, Long postId) {
        // TODO: loginUserId -> email 변환 (User/Auth 도메인 연동)
        String authorEmail = "todo@example.com";

        // 1) 이미 좋아요 눌렀는지 확인
        boolean alreadyLiked = postLikeMapper.existsByPostIdAndAuthorEmail(postId, authorEmail);
        if (alreadyLiked) {
            // TODO: 이미 좋아요인 상태면 커스텀 예외
            return;
        }

        // 2) 좋아요 INSERT
        PostLike like = PostLike.builder()
                .postId(postId)
                .authorEmail(authorEmail)
                .createdAt(LocalDateTime.now())
                .build();
        postLikeMapper.insert(like);

        // 3) posts.likes +1
        postMapper.increaseLikes(postId);
    }

    /**
     * 게시글 좋아요 취소
     * - DELETE /api/posts/{postId}/like
     */
    @Transactional
    public void unlikePost(Long loginUserId, Long postId) {
        // TODO: loginUserId -> email 변환 (User/Auth 도메인 연동)
        String authorEmail = "todo@example.com";

        // 1) 좋아요가 존재하는지 확인
        boolean alreadyLiked = postLikeMapper.existsByPostIdAndAuthorEmail(postId, authorEmail);
        if (!alreadyLiked) {
            // TODO: 이미 좋아요가 아닌 상태면 커스텀 예외
            return;
        }

        // 2) post_likes 에서 삭제
        postLikeMapper.deleteByPostIdAndAuthorEmail(postId, authorEmail);

        // 3) posts.likes -1
        postMapper.decreaseLikes(postId);
    }
}
