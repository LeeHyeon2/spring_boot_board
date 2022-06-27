package com.its.board.entity;

import com.its.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "boardTitle",length = 50 , nullable = false)
    private String boardTitle;
    @Column(name = "boardWriter",length = 20)
    private String boardWriter;
    @Column(name = "boardPassword",length = 20)
    private String boardPassword;
    @Column(name = "boardContents",length = 500)
    private String boardContents;
    @Column(name = "boardHits")
    @ColumnDefault("0") //default 0
    private int boardHits;

    public static BoardEntity toEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        return boardEntity;
    }

}
