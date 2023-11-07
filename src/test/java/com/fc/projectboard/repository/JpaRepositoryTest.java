package com.fc.projectboard.repository;

import com.fc.projectboard.config.JpaConfig;
import com.fc.projectboard.domain.Article;
import com.fc.projectboard.domain.UserAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest // Autowired 기능 가지고 있음, 롤백기능이 있어 쿼리가 실행되지만 실제 데이터가 업데이트 되지 않는다.
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("SELECT 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();
        // Then
        Assertions.assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("INSERT 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        long previousCount = articleRepository.count();

        Article saved = articleRepository.save(Article.of(
                UserAccount.of("user01", "1234", "user01@mail.com", "nick01", "memo"),
                "2 article", "2 content", ""));

        Assertions.assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("UPDATE 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {

        // Given - 해시태그 변경해보기
        Article article = articleRepository.findById(1L)
                .orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Article saved = articleRepository.saveAndFlush(article);

        // Then : 변경된 데이터 확인
        Assertions.assertThat(saved)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("DELETE 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        Article article = articleRepository.findById(1L)
                .orElseThrow();

        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        long deletedCommentsSize = article.getArticleCommentSet().size();

        // When
        articleRepository.delete(article);

        Assertions.assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount - 1);
        Assertions.assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deletedCommentsSize);

    }
}