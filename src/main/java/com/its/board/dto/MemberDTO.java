package com.its.board.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public MemberDTO(String memberEmail, String memberPassword, String memberName) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
    }


}
