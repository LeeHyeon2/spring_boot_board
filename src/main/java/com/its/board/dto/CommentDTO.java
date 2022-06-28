package com.its.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO{
    private Long id;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public CommentDTO(String commentWriter, String commentContents) {
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
    }
}
