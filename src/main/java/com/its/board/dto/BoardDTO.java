package com.its.board.dto;

import com.its.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardPassword;
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    private MultipartFile boardFile; // 실제 파일
    private String boardFileName; // 파일 이름

    public BoardDTO(String boardTitle, String boardWriter, String boardPassword, String boardContents) {
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardPassword = boardPassword;
        this.boardContents = boardContents;
    }


    public BoardDTO(Long id, String boardTitle, String boardWriter, int boardHits, LocalDateTime createdTime) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHits = boardHits;
        this.createdTime = createdTime;
    }

    public static BoardDTO toDTO(BoardEntity entity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(entity.getId());
        boardDTO.setBoardTitle(entity.getBoardTitle());
        boardDTO.setBoardWriter(entity.getBoardWriter());
        boardDTO.setBoardPassword(entity.getBoardPassword());
        boardDTO.setBoardContents(entity.getBoardContents());
        boardDTO.setBoardHits(entity.getBoardHits());
        boardDTO.setCreatedTime(entity.getCreatedTime());
        boardDTO.setUpdateTime(entity.getUpdatedTime());
        boardDTO.setBoardFileName(entity.getBoardFileName());
        return boardDTO;
    }
}
