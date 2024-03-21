package com.estsoft.blogjpa.domain.article.controller;


import com.estsoft.blogjpa.domain.article.dto.ArticleRequestDto;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.repository.ArticleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ac;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
        articleRepository.deleteAll();
    }

    @Test
    public void addArticle() throws Exception {
        //given : 저장하고 싶은 글 정보
        ArticleRequestDto request = new ArticleRequestDto("제목","내용");
        //request라는 자바 객체를 json문자열로 변환한다.
        String json = objectMapper.writeValueAsString(request);

        //when : POST /api/articles
        ResultActions resultActions = mockMvc.perform(post("/api/articles")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON));

        //then : HttpStatusCode 201검증
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("title").value(request.getTitle()))
            .andExpect(jsonPath("content").value(request.getContent()));
    }

    @Test
    public void showArticle() throws Exception {
        //given : articleRepository.saveAll
        List<Article> articleList = new ArrayList<>();
        Article article1 = new Article("title1","content1");
        Article article2 = new Article("title2","content2");
        articleList.add(article1);
        articleList.add(article2);
        articleRepository.saveAll(articleList);

        //when : GET /api/articles
        ResultActions resultActions = mockMvc.perform(get("/api/articles"));

        //호출결과(Json) : [{"title" : "title1" , "content" : "content1"},{"title" : "title2" , "content" : "content2"}]
        //then : 호출결과(Json)와 save한 데이터와 비교
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value(articleList.get(0).getTitle()))
            .andExpect(jsonPath("$[0].content").value(articleList.get(0).getContent()))
            .andExpect(jsonPath("$[1].title").value(articleList.get(1).getTitle()))
            .andExpect(jsonPath("$[1].content").value(articleList.get(1).getContent()));

    }

    @Test
    public void deleteById() throws Exception {
        //given : 삭제할 대상 데이터 save
        Article article = articleRepository.save(new Article("title","content"));
        Long id = article.getId();

        //when : DELETE /api/articles/{id}
        ResultActions resultActions = mockMvc.perform(delete("/api/articles/{id}",id));

        //then : 삭제 결과 확인, 200 응답코드 확인
        resultActions.andExpect(status().isOk());

        Optional<Article> deletedArticle = articleRepository.findById(id);
        //주어진 조건이 false인지 확인한다. false가 아닐경우 테스가 실패한다.
        assertFalse(deletedArticle.isPresent());
    }

    @Test
    public void updateArticle() throws Exception {
        //given:
        Article article = articleRepository.save(new Article("title","content"));;
        Long id = article.getId();
        ArticleRequestDto request = new ArticleRequestDto("updated title","updated content");

        //when: PUT /api/articles/{id} json {"title":"updated title","content":"updated content"}
        ResultActions resultActions = mockMvc.perform(put("/api/articles/{id}",id)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));

        //then:
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("title").value(request.getTitle()))
            .andExpect(jsonPath("content").value(request.getContent()));

        Article updatedArticle = articleRepository.findById(id).orElseThrow();
        Assertions.assertThat(updatedArticle.getTitle()).isEqualTo(request.getTitle());


    }
}