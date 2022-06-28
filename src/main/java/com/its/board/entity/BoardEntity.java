package com.its.board.entity;

import com.its.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
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
    @Column
    private String boardFileName;

    // 회원-게시글 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    // 게시글-댓글 연관관계(1:N)
    @OneToMany(mappedBy = "boardEntity" , cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // 회원 엔티티와 연관관계 맺기 전
//    public static BoardEntity toEntity(BoardDTO boardDTO){
//        BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
//        boardEntity.setBoardContents(boardDTO.getBoardContents());
//        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
//        return boardEntity;
//    }

    // 회원과 연관관계 맺은 후
    public static BoardEntity toEntity(BoardDTO boardDTO, MemberEntity memberEntity){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
        return boardEntity;
    }
}
