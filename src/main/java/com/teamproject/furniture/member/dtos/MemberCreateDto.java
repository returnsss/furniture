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

    private String birthyy;
    private String birthmm;
    private String birthdd;

    private String gender;
    private String email;

    private String mail1;
    private String mail2;

    private String address;

    private String zipcode;
    private String address1;
    private String address2;

    private String phone;

    private String phone1;
    private String phone2;
    private String phone3;

    private String receiveMail;
    private String receivePhone;
    private String agreement;
    private int state;



}
