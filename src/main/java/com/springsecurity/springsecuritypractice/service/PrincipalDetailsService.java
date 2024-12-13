package com.springsecurity.springsecuritypractice.service;

import java.lang.module.ModuleDescriptor.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecuritypractice.entity.UserRole;
import com.springsecurity.springsecuritypractice.entity.Userinfo;
import com.springsecurity.springsecuritypractice.repository.UserinfoRepository;
import com.springsecurity.springsecuritypractice.security.principal.AuthenticationDto;
import com.springsecurity.springsecuritypractice.security.principal.OAuth2UserDetails;

import jakarta.transaction.Transactional;

@Service
public class PrincipalDetailsService implements UserDetailsService{

    private UserinfoRepository userinfoReposity; 

    @Autowired
    PrincipalDetailsService(UserinfoRepository userinfoReposity){
        this.userinfoReposity = userinfoReposity;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userinfo userinfo = userinfoReposity.findByUserinfoEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        

        AuthenticationDto authenticationDto = AuthenticationDto.builder()
                                                .email(userinfo.getUserinfoEmail())
                                                .password(userinfo.getUserinfoPassword())
                                                .build();

        for(UserRole userRole : userinfo.getUserRoles()){
            authenticationDto.getRoleNames().add(userRole.getUserRoleName());
        }

        OAuth2UserDetails oAuth2UserDetails = OAuth2UserDetails.builder()
                                              .authenticationDto(authenticationDto)
                                              .build();

        return oAuth2UserDetails;
    }

}
