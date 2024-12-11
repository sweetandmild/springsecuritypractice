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
import com.springsecurity.springsecuritypractice.security.userDetails.FormLoginDto;
import com.springsecurity.springsecuritypractice.security.userDetails.PrincipalDetails;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class PrincipalDetailsService implements UserDetailsService{

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private UserinfoRepository userinfoReposity; 

    @Autowired
    PrincipalDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder
                            , UserinfoRepository userinfoReposity){

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userinfoReposity = userinfoReposity;

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userinfo userinfo = userinfoReposity.findByUserinfoEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        

        FormLoginDto formLoginDto = FormLoginDto.builder()
                                                .email(userinfo.getUserinfoEmail())
                                                .password(userinfo.getUserinfoPassword())
                                                .build();

        for(UserRole userRole : userinfo.getUserRoles()){
            formLoginDto.getRoleNames().add(userRole.getUserRoleName());
        }

        PrincipalDetails principalDetails = PrincipalDetails.builder()
                                                            .formLoginDto(formLoginDto)
                                                            .build();

        return principalDetails;
    }

}
