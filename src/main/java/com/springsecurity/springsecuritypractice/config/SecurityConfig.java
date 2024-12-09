package com.springsecurity.springsecuritypractice.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

//spring boot application의 configration으로써 사용할 bean임을 설정하는 어노테이션
@Configuration
//spring security가 관리하는 configuration 이 되도록 하는 어노테이션(메서드 단위 보안 설정을 위한 어노테이션)
// @EnableMethodSecurity

 /******************************************************************************************

 *******************************************************************************************/

 /***************************************************************

 ***************************************************************/



public class SecurityConfig {
    /**********************************************************
      특정 메서드를 bean으로 등록하여 
      spring security의 filter들이 작동하는 메커니즘을 커스텀한다.
    ***********************************************************/

    
    /**********************************************************
      비밀번호 암호화를 위한 객체 생성 메서드
    ***********************************************************/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**************************************************************
      
    ***************************************************************/
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    
    /**********************************************************
    swagger 관련 url에 대한 보안 설정(전체 접근 가능)을 위한 url String Array
    ***********************************************************/
    public static final String[] swaggerPatterns = {
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/v3/api-docs",
        "/swagger-ui.html"
    };
  

    public static final String[] ExamplePatterns = {
    /******************************************************************************************
        project website에서 "전체 접근 가능" 설정이 필요한 
        url을 모아두기 위한 string array
        ex) "/api/products/view/**", 서버에 저장된 이미지를 불러오기 위한 url
            "/api/member/context",   url 호출시 생성되는 securityContext 객체 정보를 보기위한 url
    *******************************************************************************************/
    };
   

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        /***********************************************
         cors 설정에 필요한 configurationSource 객체 생성
         - CorsConfiguration 이 들어있는 
           UrlBasedCorsConfigurationSource 객체를 반환!!
        ************************************************/
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                .configurationSource(corsConfigurationSource())
          );



        /**********************************************************
         session 관리를 하지 않겠다 STATELESS 상태를 유지하겠다는 설정
        ***********************************************************/
        http.sessionManagement(sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));



        /***********************************************************
         csrf 토큰을 통한 csrf 공격 대비에 대한 설정을 비활성화 하겠다!!
        ************************************************************/
        http.csrf(config -> config.disable());
        


        /***************************************************
          1. 각 url 패턴에 대한 접근 권한 설정
          2. {url패턴}/** --> "url패턴에 대한 하위 url 전부" 
          3. String Array로 그룹화하여 관리할 것
        ****************************************************/
        http
        
            .authorizeHttpRequests((auth)-> auth
                .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/mypage/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            );




        /*****************************************************************************
          1. http.formLogin()을 사용하면, 
             Spring Security가 내부적으로 UsernamePasswordAuthenticationFilter를 등록
             따라서 명시적으로 등록할 필요X

          2. 기본적으로 UsernamePasswordAuthenticationFilter는 로그인 요청 URL이 
             /login이라고 가정
             , .loginProcessingUrl("/api/user/login")를 통해 특정 url패턴 등록 가능
             , POST 요청으로 아이디(username)와 비밀번호(password)
        *******************************************************************************/
        http

            .formLogin((auth) -> auth
                /***************************************************
                 인증되지 않은 사용자가 인증이 필요한 리소스의 요청을
                 시도할 경우 리다이렉션시킬 URL
                 (웹 사이트에서 제공하는 로그인 페이지 URL)
                ***************************************************/
                .loginPage("/user/login")

                /***************************************************
                 UsernamePasswordAuthenticationFilter가 작동되기 위한
                 login 요청 url (POST 방식으로 호출해야함)
                ***************************************************/
                .loginProcessingUrl("/api/user/login")

                /***********************************************
                  >> 인증이 성공? 했을 경우에 대한 핸들러
                 ***********************************************/
                .successHandler(null)
                
                
                /***********************************************
                  >> 인증이 실패? 했을 경우에 대한 핸들러
                 ***********************************************/
                .failureHandler(null)
            );


        return http.build();

     
    }

    
}
