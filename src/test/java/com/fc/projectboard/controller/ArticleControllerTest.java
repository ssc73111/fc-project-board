package com.fc.projectboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("뷰 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class) // 설정 한 클래스만 실행
class ArticleControllerTest {

    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticleView() throws Exception {
        mvc.perform(get("/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            .andExpect(model().attributeExists("articles")); // 해당 이름의 데이터가 있는지 확인
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        mvc.perform(get("/articles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/detail"))
            .andExpect(model().attributeExists("article")) // 해당 이름의 데이터가 있는지 확인
            .andExpect(model().attributeExists("articleComments")); // 해당 이름의 데이터가 있는지 확인
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        mvc.perform(get("/articles/search"))
            .andExpect(status().isOk())
            .andExpect(view().name("articles/search "))
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleSearchView() throws Exception {
        mvc.perform(get("/articles/search-hashtag"))
            .andExpect(status().isOk())
            .andExpect(view().name("articles/articles-hashtag"))
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}