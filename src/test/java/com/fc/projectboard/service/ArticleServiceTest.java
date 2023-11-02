package com.fc.projectboard.service;

import com.fc.projectboard.domain.Article;
import com.fc.projectboard.domain.type.SearchType;
import com.fc.projectboard.dto.ArticleDto;
import com.fc.projectboard.dto.ArticleUpdateDto;
import com.fc.projectboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비지니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    //홈 버튼 -> 게시판 페이지로 리다이렉션


    //    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
//    @Test
//    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
//        // Given
//
//        // When
//        List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그
//
//        // Then
//        Assertions.assertThat(articles).isNotNull();
//    }
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsPage() {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        Assertions.assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticles_thenReturnsArticle() {
        // Given
        long articleId = 1l;
        // When
        ArticleDto article = sut.searchArticle(articleId); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        Assertions.assertThat(article).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        //given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "writer", "title", "content", "#first");
//        willDoNothing().
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //when
        sut.saveArticle(dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenSavingArticle_thenUpdatesArticle() {
        //given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title", "content", "#first");
//        willDoNothing().
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //when
        sut.updateArticle(1L, dto);
        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticles() {
        willDoNothing().given(articleRepository).delete(any(Article.class));

        sut.deleteArticle(1L);

        then(articleRepository).should().delete(any(Article.class));
    }
}