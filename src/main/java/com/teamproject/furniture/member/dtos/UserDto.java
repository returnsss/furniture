package com.teamproject.furniture.member.dtos;

import com.teamproject.furniture.member.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDto implements UserDetails {

    private String userId;
    private String password;
    private int state;

    public UserDto(Member member) {
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.state = member.getState();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (state == 3) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    /**
     * 사용자 계정의 만료 여부를 반환합니다.
     * 반환값이 true인 경우 계정이 만료되지 않았음을 의미합니다.
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 사용자 계정이 잠겨 있는지 여부를 반환합니다.
     * 반환값이 true인 경우 계정이 잠겨 있지 않음을 의미합니다.
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 사용자 인증 정보(비밀번호)의 만료 여부를 반환합니다.
     * 반환값이 true인 경우 인증 정보가 만료되지 않았음을 의미합니다.
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 계정이 활성화되었는지 여부를 반환합니다.
     * 반환값이 true인 경우 계정이 활성화되었음을 의미합니다.
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
