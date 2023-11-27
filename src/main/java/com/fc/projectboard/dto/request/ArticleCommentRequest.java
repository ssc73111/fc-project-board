package com.fc.projectboard.dto.request;

import com.fc.projectboard.dto.ArticleCommentDto;
import com.fc.projectboard.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, Long parentCommentId, String content) {
    
    public static ArticleCommentRequest of(Long articleId, String content) {
        return ArticleCommentRequest.of(articleId, null, content);
    }

    public static ArticleCommentRequest of(Long articleId, Long parentCommentId, String content) {
        return new ArticleCommentRequest(articleId, parentCommentId, content);
    }

    // 인증을 통해서 넣을 때
    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(articleId, userAccountDto, parentCommentId, content);
    }
}
