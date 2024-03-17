package com.estsoft.blogjpa.dto;

import java.time.LocalDateTime;
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
public class CommentResponse {//서버에서 유저의 댓글 정보조회에 응답할때
    private Long id;
    private String body;
    private LocalDateTime createdAt;
}
