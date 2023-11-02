package com.fc.projectboard.service;

import com.fc.projectboard.domain.ArticleComment;
import com.fc.projectboard.repository.ArticleCommentRepository;
import com.fc.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommandRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ArticleComment> searchArticleComment(long articleId) {
        return List.of();
    }
}
