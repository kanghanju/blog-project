package com.estsoft.blogjpa.domain.comment.dto;

import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {
    private String body;

    public Comment toEntity(Article article) {//CommentRequestDto -> Comment
        return Comment.builder()
            .article(article)
            .body(body)
            .build();
    }
}
