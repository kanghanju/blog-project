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
public class ArticleResponseDto {
    private String title;
    private String content;

    public ArticleResponseDto(Article article) {//Article -> ArticleReponseDto로 바꿔서 클라이언트에 응답객체를 내보내기 위함
        //getAllArticles에 쓰였다.
        title = article.getTitle();
        content = article.getContent();
    }
}
