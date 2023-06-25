package com.teamproject.furniture.member.model;

import com.teamproject.furniture.member.dtos.MemberCreateDto;
import com.teamproject.furniture.member.dtos.MemberUpdateDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate // 수정된 부분만 쿼리문 만들도록
@NoArgsConstructor // 기본 생성자
@EntityListeners(value = {AuditingEntityListener.class})
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
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registDay;
    private int state;

    public static final int STATE_USER = 0;         // 일반회원
    public static final int STATE_LIMIT = 1;       // 제재된 회원
    public static final int STATE_WITHDRAWAL = 2;   // 탈퇴한 회원
    public static final int STATE_ADMIN = 3;        // 관리자


    public Member(String userId, String password, String name, String birth, String gender, String email, String address, String phone, String receiveMail, String receivePhone, String agreement) {

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

    public void updateStateUser(Long memberId){
        this.state = STATE_USER;
    }

    public void updateStateLimit(Long memberId) {
        this.state = STATE_LIMIT;
    }

    public void updateStateWithdrawal(Long memberId) {
        this.state = STATE_WITHDRAWAL;
    }

    public void updateStateAdmin(Long memberId) {
        this.state = STATE_ADMIN;
    }
}
