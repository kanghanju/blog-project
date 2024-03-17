package com.estsoft.blogjpa.domain.article.dto;

import com.estsoft.blogjpa.domain.article.entity.Article;
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
public class ArticleRequestDto {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
            .title(title)
            .content(content)
            .build();
    }
}
