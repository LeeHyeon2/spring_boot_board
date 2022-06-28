package com.its.board.entity;

import com.its.board.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member_table" )
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    // 회원(1)-게시글(N) 연관관계
    // delete 관련 옵션 없는 경우
//    @OneToMany(mappedBy = "memberEntity")
//    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    // set null로 지정시 삭제 전에 member_id 컬럼을 null로
    @PreRemove
    private void preRemove(){
        commentEntityList.forEach(comment -> comment.setMemberEntity(null));
    }


    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }

}
