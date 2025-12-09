package com.yumyumcoach.domain.community.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private int likes;
    private String authorEmail;

    public void update(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }

    public static Post newPost(String title, String content, String category, String authorEmail) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .authorEmail(authorEmail)
                .createdAt(LocalDateTime.now())
                .likes(0)
                .build();
    }
}
