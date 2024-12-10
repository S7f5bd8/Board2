package com.example.board2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // @PreAuthorize, @Secured 어노테이션 사용 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // 권한 및 요청 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/homepage/**", "/assets/**").permitAll() // 공용 경로
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 전용 경로
                        .requestMatchers("/user/**").hasRole("USER") // 사용자 전용 경로
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )

                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .loginProcessingUrl("/perform_login") // 로그인 처리 URL
                        .defaultSuccessUrl("/homepage/home", true) // 로그인 성공 후 이동 경로
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동 경로
                        .permitAll() // 로그인 경로 접근 허용
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/perform_logout") // 로그아웃 처리 URL
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                )

                // 권한 부족 예외 처리
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied") // 권한 부족 시 이동 경로
                );

        return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // AuthenticationManager 설정
    }
}
