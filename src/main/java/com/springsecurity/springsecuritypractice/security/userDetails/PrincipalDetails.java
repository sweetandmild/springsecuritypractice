package com.springsecurity.springsecuritypractice.security.userDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PrincipalDetails implements UserDetails{

    /**********************************************************************************
      <formLogin 인증과정에서 필요한 데이터를 담기 위한 DTO>
      : UserDetailsService 구현 객체의 loadUserByUsername() 메서드 상에서
        인증하려는 회원의 정보를 DB에서 가져왔을때의 데이터를 담는다.
     **********************************************************************************/
    private FormLoginDto formLoginDto;


    /**********************************************************************************
      <oauth2Login 인증과정에서 필요한 데이터를 담기 위한 DTO>
      : DefaultOAuth2UserService 구현 객체의 loadUser() 메서드 상에서
        인증하려는 회원의 정보를 인증을 대신 처리한 사이트로부터 받아왔을때의 데이터를 담는다.
     **********************************************************************************/    
    private OAuth2LoginDto OAuth2LoginDto;




    /*****************************************************
      UserDetails 구현 스펙
      1. getAuthorities()

      2. getUsername()

      3. getPassword()
     *****************************************************/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
         formLoginDto.getRoleNames().forEach(roleName 
                -> authorities.add(new SimpleGrantedAuthority(roleName)));

        return authorities;
    }

    @Override
    public String getUsername() {
        return formLoginDto.getEmail();
    }

    @Override
    public String getPassword() {
        return formLoginDto.getPassword();
    }

    /**********************************************
       
     **********************************************/
    public Map<String, Object> getClaims() {
      
      Map<String, Object> claims = new HashMap<>();

      claims.put("email", formLoginDto.getEmail());
      claims.put("roleNames", formLoginDto.getRoleNames());
      claims.put("social", formLoginDto.isSocial());

      return claims;
    }

}
