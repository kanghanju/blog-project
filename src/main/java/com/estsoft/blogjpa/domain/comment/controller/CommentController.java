package com.estsoft.blogjpa.domain.comment.controller;

import com.estsoft.blogjpa.domain.comment.dto.CommentRequestDto;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponseDto;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import com.estsoft.blogjpa.domain.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long articleId,@RequestBody
        CommentRequestDto commentRequestDto) {
        Comment comment = commentService.saveComment(articleId,commentRequestDto);
        return ResponseEntity.ok(comment.toResponse());
    }

    @GetMapping("/{articleId}/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable(value = "articleId") Long articleId,@PathVariable Long commentId) {
        Comment comment = commentService.findComment(articleId,commentId);
        return ResponseEntity.ok(comment.toResponse());
    }
}
