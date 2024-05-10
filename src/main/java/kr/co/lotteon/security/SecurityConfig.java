package kr.co.lotteon.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // 로그인 설정
        httpSecurity.formLogin(login -> login
                                        .loginPage("/member/login")
                                        .defaultSuccessUrl("/")
                                        .failureUrl("/member/login?success=100")
                                        .usernameParameter("uid")
                                        .passwordParameter("pass"));

        // 로그아웃 설정
        httpSecurity.logout(logout -> logout
                                        .invalidateHttpSession(true)
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                        .logoutSuccessUrl("/member/login?success=300"));

        //OAuth 설정
        httpSecurity.oauth2Login(oauth -> oauth
                .loginPage("/user/login")
                .defaultSuccessUrl("/"));

        /*
            인가 설정
             - Spring Security는 존재하지 않는 요청 주소에 대해 기본적으로 login 페이지로 redirect를 수행
             - 자원 요청의 추가 인가 처리 확장과 redirect 기본 해제를 위해 마지막에 .anyRequest().permitAll() 설정
         */
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                                                        .requestMatchers("/article/**").permitAll()
                                                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                                        .requestMatchers("/seller/**").hasAuthority("SELLER")
                                                        .requestMatchers("/manager/**").hasAnyAuthority("ADMIN", "MANAGER")
                                                        .anyRequest().permitAll());

        // 사이트 위변조 방지 설정
        httpSecurity.csrf(CsrfConfigurer::disable);

        return httpSecurity.build();
    }

    // Security 인증 암호화 인코더 설정
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // 계정 탈퇴
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
