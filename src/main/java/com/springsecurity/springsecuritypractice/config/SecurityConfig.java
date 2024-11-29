package com.springsecurity.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//spring boot application이 configration으로써 사용할 bean임을 설정하는 어노테이션
@Configuration


//spring security가 관리하는 configuration 이 되도록 하는 어노테이션
@EnableWebSecurity
public class SecurityConfig {
    /**********************************************************
      특정 메서드를 bean으로 등록하여 
      spring security의 filter들이 작동하는 메커니즘을 커스텀한다.
    ***********************************************************/

    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        
            .authorizeHttpRequests((auth)-> auth
                .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/mypage/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                );

        http

            .formLogin((auth) -> auth
                .loginPage("/login")
                .loginProcessingUrl("/loginProc").permitAll()
            );


      

        http

            .csrf( (auth) -> auth.disable() );

        return http.build();
    }
}
