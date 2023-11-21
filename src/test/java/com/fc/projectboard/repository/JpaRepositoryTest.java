package com.fc.projectboard.repository;

import com.fc.projectboard.domain.Article;
import com.fc.projectboard.domain.Hashtag;
import com.fc.projectboard.domain.UserAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@DisplayName("JPA 연결 테스트")
//@Import(JpaConfig.class)
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest // Autowired 기능 가지고 있음, 롤백기능이 있어 쿼리가 실행되지만 실제 데이터가 업데이트 되지 않는다.
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    private final HashtagRepository hashtagRepository;

    JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository,
            @Autowired HashtagRepository hashtagRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
        this.hashtagRepository = hashtagRepository;
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
                .hasSize(123); // classpath:resources/data.sql 참조
    }

    @DisplayName("INSERT 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("user03", "pw1234", null, null, null));

        Article article = articleRepository.save(Article.of(userAccount, "2 article", "2 content"));
        article.addHashtags(Set.of(Hashtag.of("spring")));
        articleRepository.save(article);

        Assertions.assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("UPDATE 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {

        // Given - 해시태그 변경해보기
        Article article = articleRepository.findById(1L)
                .orElseThrow();
//        String updatedHashtag = "#springboot";
//        article.setHashtag(updatedHashtag);
        Hashtag updatedHashtag = Hashtag.of("boot");
        article.clearHashtags();
        article.addHashtags(Set.of(updatedHashtag));

        // When
        Article saved = articleRepository.saveAndFlush(article);

        // Then : 변경된 데이터 확인
//        Assertions.assertThat(saved)
//                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
        Assertions.assertThat(saved.getHashtags())
                .hasSize(1)
                .extracting("hashtagName", String.class)
                .containsExactly(updatedHashtag.getHashtagName());
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

    @DisplayName("[Querydsl] 전체 해시태그 리스트에서 이름만 조회하기")
    @Test
    void givenNothing_whenQueryingHashtags_thenReturnsHashtagNames() {
        List<String> hashtagNames = hashtagRepository.findAllHashtagNames();

        Assertions.assertThat(hashtagNames).hasSize(19);
    }

    @DisplayName("[Querydsl] 해시태그로 페이징 된 게시글 검색하기")
    @Test
    void givenHashtagNamesAndPageable_whenQueryingArticles_thenReturnsArticlePage() {

        // Given
        List<String> hashtagNames= List.of("blue","yellow", "black");
        Pageable pageable = PageRequest.of(0, 5, Sort.by(
                Sort.Order.desc("hashtags.hashtagName"),
                Sort.Order.asc("title")

        ));
        // When
        Page<Article> articlePage = articleRepository.findByHashtagNames(hashtagNames, pageable);

        // Then
        Assertions.assertThat(articlePage.getContent()).hasSize(pageable.getPageSize());
        Assertions.assertThat(articlePage.getContent().get(0).getTitle()).isEqualTo("");
        Assertions.assertThat(articlePage.getContent().get(0).getHashtags()).extracting("hashtagName", String.class).containsExactly("");
        Assertions.assertThat(articlePage.getTotalElements()).isEqualTo(17);
        Assertions.assertThat(articlePage.getTotalPages()).isEqualTo(4);
    }


    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {

        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("uno");
        }
    }
}