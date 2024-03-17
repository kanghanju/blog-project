package com.estsoft.blogjpa.domain.article.controller;

import com.estsoft.blogjpa.domain.article.dto.ArticleRequestDto;
import com.estsoft.blogjpa.domain.article.dto.ArticleResponseDto;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<ArticleResponseDto> addArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        Article article = articleService.save(articleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(article.toResponse());
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleResponseDto>> getAllArticles() {
        List<Article> articleList = articleService.findAll();
        //TODO toList()와 collect(Collectors.toList())차이 알아보기
        // x -> new ArticleResponseDto(x) 로 작성했지만 x.toResponse()로 사용해도 될듯? 하지만 무슨역할을 하는지 알아보기가 힘들다
        List<ArticleResponseDto> response = articleList.stream().map(x -> x.toResponse()).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getOneArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id).toResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneArticle(@PathVariable Long id) {
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    //이거왜 return ArticleResponse가 아닐까?... update한 article의 세부 정보까지 보여주기 위함인가(추측)
    public ResponseEntity<Article>  updateOneArticle(@PathVariable Long id,@RequestBody ArticleRequestDto request) {
        Article updatedArticle = articleService.update(id,request);
        return ResponseEntity.ok(updatedArticle);
    }

}
