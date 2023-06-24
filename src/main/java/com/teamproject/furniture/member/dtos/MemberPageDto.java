package com.teamproject.furniture.member.dtos;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberPageDto {
    private Long memberId;
    private String userId;
    private String name;
    private String birth;
    private String gender;
    private String email;
    private String address;
    private String phone;
    private int state;


    @QueryProjection
    public MemberPageDto(Long memberId, String userId, String name, String birth, String gender, String email, String address, String phone, int state) {
        this.memberId = memberId;
        this.userId = userId;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.state = state;
    }

}
