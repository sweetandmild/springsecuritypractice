package com.springsecurity.springsecuritypractice.security.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OAuth2UserDetails implements UserDetails, OAuth2User{

    /*****************************************************************************
      <인증과정에서 필요한 데이터를 담기 위한 DTO>
      : UserDetailsService 구현 객체의 loadUserByUsername() 메서드 상에서
        인증하려는 회원의 (DB상에서 가져온) 정보 혹은
        DefaultOAuth2LoginService를 상속하여 확장한 객체의 loadUser() 메서드 상에서
        인증(가입)하려는 회원의 정보를 담는다.
    ******************************************************************************/
    private AuthenticationDto authenticationDto;

    /*******************************************
      UserDetails 구현 스펙
      1. getAuthorities()

      2. getUsername()

      3. getPassword()
    ********************************************/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Collection<GrantedAuthority> authorities = new ArrayList<>();
      authenticationDto.getRoleNames().forEach(roleName 
              -> authorities.add(new SimpleGrantedAuthority(roleName)));

      return authorities;
    }

    @Override
    public String getUsername() {
      return authenticationDto.getEmail();
    }

    @Override
    public String getPassword() {
      return authenticationDto.getPassword();
    }

    /**********************************************
       
     **********************************************/
    public Map<String, Object> getClaims() {
      
      Map<String, Object> claims = new HashMap<>();

      claims.put("id", authenticationDto.getId());
      claims.put("email", authenticationDto.getEmail());
      claims.put("roleNames", authenticationDto.getRoleNames());
      claims.put("social", authenticationDto.isSocial());
      claims.put("provider", authenticationDto.getProvider());
      claims.put("attributes", authenticationDto.getAttributes());

      return claims;
    }



    /*******************************************
      UserDetails 구현 스펙
      1. getAuthorities()

      2. getName()

      3. getAttributes()
     ******************************************/
    @Override
    public Map<String, Object> getAttributes() {
      return authenticationDto.getAttributes();
    }

    @Override
    public String getName() {
      // 위임받아 인증을 처리한 사이트의 고유아이디 혹은 이름
      return authenticationDto.getProvider();
    }

}
