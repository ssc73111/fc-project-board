package com.fc.projectboard.dto;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.fc.projectboard.domain.ArticleComment}
 */
public record ArticleCommentDto(
        LocalDateTime createdAt,
        String createBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
) {
    public static ArticleCommentDto of(LocalDateTime createdAt, String createBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
        return new ArticleCommentDto(createdAt, createBy, modifiedAt, modifiedBy, content);
    }
}