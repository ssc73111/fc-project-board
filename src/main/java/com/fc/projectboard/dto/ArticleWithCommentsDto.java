package com.fc.projectboard.dto;

import com.fc.projectboard.domain.Article;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;

public record ArticleWithCommentsDto(
        Long id,
        UserAccountDto userAccountDto,
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleWithCommentsDto of(Long id,
                                            UserAccountDto userAccountDto,
                                            Set<ArticleCommentDto> articleCommentDtos,
                                            String title,
                                            String content,
                                            String hashtag,
                                            LocalDateTime createdAt,
                                            String createdBy,
                                            LocalDateTime modifiedAt,
                                            String modifiedBy) {
        return new ArticleWithCommentsDto(id,
                userAccountDto,
                articleCommentDtos,
                title,
                content,
                hashtag,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy);
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return new ArticleWithCommentsDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleCommentSet().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)), // 순서보장
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
