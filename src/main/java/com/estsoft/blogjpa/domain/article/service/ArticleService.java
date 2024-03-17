package com.estsoft.blogjpa.domain.article.service;

import com.estsoft.blogjpa.domain.article.dto.ArticleRequestDto;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.repository.ArticleRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article save(ArticleRequestDto articleRequestDto) {
        return articleRepository.save(articleRequestDto.toEntity());
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found article " + id));
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    //TODO @Transactional 내부 동작
    @Transactional
    public Article update(Long id, ArticleRequestDto request) {
        //begin transaction 일단 업데이트 할 article이 있는지 탐색한다
        Article article = findById(id);
        article.update(request.getTitle(),request.getContent());
        //commit / rollback
        return article;
    }
}
