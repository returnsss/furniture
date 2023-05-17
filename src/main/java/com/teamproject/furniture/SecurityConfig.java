package com.teamproject.furniture;

import com.teamproject.furniture.member.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hibernate.internal.CoreLogging.logger;

@Configuration
@EnableWebSecurity // Spring Security 설정을 시작
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


        http.authorizeRequests()
                .antMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        // 익명 객체 사용
        // 익명 객체 사용
        http.formLogin() // form 로그인 인증 기능이 작동함
                .loginPage("/login") // 사용자 정의 로그인 페이지, default: /login
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
                .failureUrl("/login") // 로그인 실패 후 이동 페이지
                .usernameParameter("userId") // 아이디 파라미터명 설정, default: username
                .passwordParameter("passwd") // 패스워드 파라미터명 설정, default: password
                .loginProcessingUrl("/user/login") // 로그인 Form Action Url, default: /login
                .successHandler( // 로그인 성공 후 핸들러
                        (request, response, authentication) -> {
                            logger("authentication: " + authentication.getName());
                            response.sendRedirect("/");
                        })
                .failureHandler( // 로그인 실패 후 핸들러
                        (request, response, exception) -> {
                            logger("exception: " + exception.getMessage());
                            response.sendRedirect("/login");
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
                    response.sendRedirect("/login");
                }) // 로그아웃 성공 핸들러
                .deleteCookies("JSESSIONID"); // 로그아웃 후 삭제할 쿠키 지정
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
