package com.teamproject.furniture.member.model;

import com.teamproject.furniture.member.dtos.MemberCreateDto;
import com.teamproject.furniture.member.dtos.MemberUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
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
    private String registDay;
    private int state;


    public Member(Long memberId, String userId, String password, String name, String birth, String gender, String email, String address, String phone, String receiveMail, String receivePhone, String agreement, String registDay, int state) {
        //this.memberId = memberId;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.receiveMail = receiveMail;
        this.receivePhone = receivePhone;
        this.agreement = agreement;
        //this.regist_day = regist_day;
        //this.state = state;
    }

    public Member(MemberCreateDto memberCreateDto){
        this.userId = memberCreateDto.getUserId();
        this.password = memberCreateDto.getPassword();
        this.name = memberCreateDto.getName();
        this.birth = memberCreateDto.getBirth();
        this.gender = memberCreateDto.getGender();
        this.email = memberCreateDto.getEmail();
        this.address = memberCreateDto.getAddress();
        this.phone = memberCreateDto.getPhone();
        this.receiveMail = memberCreateDto.getReceiveMail();
        this.receivePhone = memberCreateDto.getReceivePhone();
        this.agreement = memberCreateDto.getAgreement();
    }



    // update메소드 작성
    // member에 update함수를 만들어서 인자값으로 MemberUpdateDto 값이 쓰인다
    public void update(MemberUpdateDto memberUpdateDto){

        this.password = memberUpdateDto.getPassword();
        this.name = memberUpdateDto.getName();
        this.birth = memberUpdateDto.getBirth();
        this.gender = memberUpdateDto.getGender();
        this.email = memberUpdateDto.getEmail();
        this.address = memberUpdateDto.getAddress();
        this.phone = memberUpdateDto.getPhone();
        this.receiveMail = memberUpdateDto.getReceiveMail();
        this.receivePhone = memberUpdateDto.getReceivePhone();
        this.agreement = memberUpdateDto.getAgreement();


    }

}
