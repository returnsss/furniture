package com.teamproject.furniture.config;

import com.teamproject.furniture.member.dtos.UserDto;
import com.teamproject.furniture.member.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.teamproject.furniture.member.model.Member.STATE_LIMIT;
import static com.teamproject.furniture.member.model.Member.STATE_WITHDRAWAL;


@Configuration
@EnableWebSecurity // Spring Security 설정을 시작
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()        //cors방지
                .csrf().disable()        //csrf방지
                .formLogin().disable()    //기본 로그인 페이지 없애기
                .headers().frameOptions().disable();


        http
                .authorizeRequests()    // 요청에대한 권한부여 설정
                .antMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN") // /admin/ 또는 /api/admin/으로 시작하는 경로에 대해서는 ADMIN 역할을 가진 사용자만 접근 할수 있다.
                //todo 추후에 아래거 지우고 이 메서드 사용 .antMatchers("/api/member").permitAll() // /api/member 경로에 대해서는 인증되지 않은 사용자도 접근할수있다.
                .antMatchers("/","/member/login","/member/addmember","/js/**","/css/**","/images/**"
                        ,"/assets/**","/api/validate","/api/member").permitAll()
                .antMatchers("/member/loginFailed").hasAnyRole("WITHDRAWAL","LIMIT")
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated(); // 위에서 설정한 경로 이외의 모든 요청은 인증된 사용자만 접근할 수 있습니다.



        http.formLogin() // form 로그인 인증 기능이 작동함
                .loginPage("/member/login") // 사용자 정의 로그인 페이지, default: /login
                .failureUrl("/member/login?error=1") // 로그인 실패 후 이동 페이지
                .usernameParameter("userId") // 아이디 파라미터명 설정, default: username
                .passwordParameter("password") // 패스워드 파라미터명 설정, default: password
                .loginProcessingUrl("/member/login") // 로그인 Form Action Url, default: /login, 폼받고 post일때 작동
                .successHandler((request, response, exception) -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                    int state = ((UserDto) authentication.getPrincipal()).getState();

                    if (state == STATE_WITHDRAWAL || state == STATE_LIMIT) {
                        try {
                            response.sendRedirect("/member/loginFailed");
                            return;
                        } catch (IOException e) {
                            log.error("", e);
                        }
                    }
                    response.sendRedirect("/");
                })
                .permitAll(); // loginPage 접근은 인증 없이 접근 가능

        http.logout()
                .logoutUrl("/logout")   // 로그아웃 처리 URL (= form action url)
                //.logoutSuccessUrl("/login") // 로그아웃 성공 후 targetUrl,
                // logoutSuccessHandler 가 있다면 효과 없으므로 주석처리.
                .addLogoutHandler((request, response, authentication) -> {
                    // 사실 굳이 내가 세션 무효화하지 않아도 됨.
                    // LogoutFilter가 내부적으로 해줌.
                    HttpSession session = request.getSession();
                    if (session != null) {
                        session.invalidate();
                    }
                })  // 로그아웃 핸들러 추가
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/member/login");
                }) // 로그아웃 성공 핸들러
                .deleteCookies("JSESSIONID"); // 로그아웃 후 삭제할 쿠키 지정
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
