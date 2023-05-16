package com.teamproject.furniture.member.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {

    private String userId;
    private String password;
    private String name;
    private String birth;
    private String gender;
    private String email;
    private String address;
    private String phone;
    private String receiveMail;
    private String receivePhone;
    private String agreement;
    private int state;



}
