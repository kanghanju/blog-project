package com.estsoft.blogjpa.domain.comment.entity;

import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id",updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "article_id",nullable = false)
    private Article article;

    @Column(name  = "body",nullable = false)
    private String body;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Comment(Article article,String body) {
        this.article = article;
        this.body = body;
    }

    public CommentResponseDto toResponse() {
        return CommentResponseDto.builder()
            .id(id)
            .body(body)
            .createdAt(createdAt)
            .build();
    }
}
