package com.estsoft.blogjpa.domain.comment.service;

import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.repository.ArticleRepository;
import com.estsoft.blogjpa.domain.comment.dto.CommentRequestDto;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import com.estsoft.blogjpa.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public CommentService(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Long articleId, CommentRequestDto commentRequestDto) {
        //먼저 articleId이 존재하는지 검사한다
        Article article =  articleRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("not found article " + articleId));
        //후에 저장
        return commentRepository.save(commentRequestDto.toEntity(article));
    }

    public Comment findComment(Long articleId, Long commentId) {
        //articleId와 commentId가 유효한지 검사한다
        //TODO 아래의 articleRepository.findById...코드가 계속 중복되는데 이게맞나 ..
        Article article = articleRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("not found article " + articleId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("not found comment" +commentId));

        //TODO getArticle을 하고 getId를 하는 JPA의 세계란 신기하다..내부 동작을 찾아봐야할듯
        if(!comment.getArticle().getId().equals(articleId)) {
            //TODO 어떤종류의 예외를 던질지 고민해보기 , 에러메시지도 어떻게 명확하게 작성해야할지도 고민해봐야겠다...
            throw new IllegalArgumentException("articleId : " + articleId + " doesn't match to commentId : " + commentId);
        }

        return comment;
    }
}
