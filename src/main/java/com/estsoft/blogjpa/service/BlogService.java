package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.dto.CommentRequest;
import com.estsoft.blogjpa.dto.CommentResponse;
import com.estsoft.blogjpa.external.ExampleAPIClient;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.repository.CommentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final ExampleAPIClient apiClient;

    public BlogService(BlogRepository blogRepository, CommentRepository commentRepository,
        ExampleAPIClient apiClient) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.apiClient = apiClient;
    }

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id" + id));
//        return blogRepository.findById(id).orElse(new Article());
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, AddArticleRequest request) {
        // begin transaction
        Article article = findById(id);
        article.update(request.getTitle(), request.getContent());
        // commit / rollback
        return article;
    }

    @Transactional
    public Article updateTitle(Long id, AddArticleRequest request) {
        Article article = findById(id);
        blogRepository.updateTitle(id, request.getTitle());
        return article;
    }

    public List<Article> saveBulkArticles() {
        List<Article> articles = parsedArticles();
        return blogRepository.saveAll(articles);
    }

    private List<Article> parsedArticles() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        String responseJson = apiClient.fetchDataFromApi(url);

        ObjectMapper objectMapper = new ObjectMapper();
        List<LinkedHashMap<String, String>> jsonMapList = new ArrayList<>();

        try {
            jsonMapList = objectMapper.readValue(responseJson, List.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse json", e.getMessage());
        }

        return jsonMapList.stream()
                .map(hashMap -> new Article(hashMap.get("title"), hashMap.get("body")))
                .toList();
    }

    public Comment saveComment(Long articleId, CommentRequest commentRequest){
        Article article = blogRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("not found article " + articleId));

        Comment comment = Comment.builder()
            .article(article)
            .body(commentRequest.getBody())
            .build();

        return commentRepository.save(comment);
    }

    public Comment findComment(Long articleId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("not found comment" +commentId));

        //TODO getArticle을 하고 getId를 하는 JPA의 세계란 신기하다..내부 동작을 찾아봐야할듯
        if(comment.getArticle().getId().equals(articleId)) {
            //TODO 어떤종류의 예외를 던질지 고민해보기 , 에러메시지도 어떻게 명확하게 작성해야할지도 고민해봐야겠다...
            throw new IllegalArgumentException("articleId : " + articleId + " doesn't match to commentId : " + commentId);
        }

        return comment;
    }
}
