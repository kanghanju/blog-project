package com.estsoft.blogjpa.domain.article.entity;

import com.estsoft.blogjpa.domain.article.dto.ArticleResponseDto;
import com.estsoft.blogjpa.domain.article.dto.ArticleViewResponse;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id",updatable = false)
    private Long id;

    @OneToOne(mappedBy = "article")
    private Comment comment;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    //TODO @UpdateTimeStamp와 차이 알아보기
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Article(String title,String content) {//ArticleRequestDto에서 사용한다
        this.title = title;
        this.content = content;
    }

    public ArticleResponseDto toResponse() {//Article -> ArticleResponseDto로 변환해서 클라이언트에게 넘겨주기 위함이다
        return ArticleResponseDto.builder()
            .title(title)
            .content(content)
            .build();
    }

    public void update(String title,String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleViewResponse toViewResponse() {
        return new ArticleViewResponse(id,title,content,createdAt,updatedAt);
    }
}
