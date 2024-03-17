package com.estsoft.blogjpa.domain.article.controller;

import com.estsoft.blogjpa.domain.article.dto.ArticleViewResponse;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/articles")
public class ArticlePageController {

    private final ArticleService articleService;

    public ArticlePageController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public String showAllArticles(Model model) {
        List<Article> articleList = articleService.findAll();
        model.addAttribute("articles",articleList);
        return "articleList";
    }

    @GetMapping("/{id}")
    public String showOneArticle(@PathVariable Long id,Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article",article.toViewResponse()); //엔티티와 뷰의 분리
        return "article";
    }

    @GetMapping("/new") //articles/new?id={id}면 수정화면 , /articles/new면 등록화면
    public String newArticle(@RequestParam(required = false) Long id,Model model) {
        log.info("id = {}",id);

        if(id == null) {//새로운 글을 등록
            model.addAttribute("article",new ArticleViewResponse());
        }else {//기존에 등록되어 있던 글을 수정
            Article article = articleService.findById(id);
            model.addAttribute("article",new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
