package com.fc.projectboard.service;

import com.fc.projectboard.domain.Article;
import com.fc.projectboard.domain.ArticleComment;
import com.fc.projectboard.repository.ArticleCommentRepository;
import com.fc.projectboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
class ArticleCommentServiceTest {
    @InjectMocks
    private ArticleCommentService sut;
    @Mock
    private ArticleCommentRepository articleCommentRepository;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글 ID로 조회하면 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsArticleComments() {
        Long articleId = 1L;
        Article article = Article.of("title", "content", "#java");
        // article.getArticleComment();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        List<ArticleComment> articleComments = sut.searchArticleComment(articleId);

        Assertions.assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 정보를 입력하면 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }
}