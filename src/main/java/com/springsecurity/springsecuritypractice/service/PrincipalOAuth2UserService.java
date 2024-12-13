package com.springsecurity.springsecuritypractice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springsecurity.springsecuritypractice.entity.UserRole;
import com.springsecurity.springsecuritypractice.entity.Userinfo;
import com.springsecurity.springsecuritypractice.repository.UserinfoRepository;
import com.springsecurity.springsecuritypractice.security.principal.AuthenticationDto;
import com.springsecurity.springsecuritypractice.security.principal.OAuth2UserDetails;

public class PrincipalOAuth2UserService extends DefaultOAuth2UserService{

    private UserinfoRepository userinfoRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    PrincipalOAuth2UserService(){};

    @Autowired
    PrincipalOAuth2UserService(UserinfoRepository userinfoRepository
                              ,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userinfoRepository = userinfoRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String)attributes.get("email");
        String provider = userRequest.getClientRegistration().getRegistrationId();

        AuthenticationDto authenticationDto = AuthenticationDto.builder()
                                                               .email((String)attributes.get("email"))
                                                               .attributes(attributes)
                                                               .provider(provider)
                                                               .isSocial(true)
                                                               .build(); 

        Userinfo findUserinfo = userinfoRepository.findByUserinfoEmail(email).get();
        if(findUserinfo == null){
            String defaultNickname = provider + "_user" + UUID.randomUUID().toString().substring(0, 6);
            String defaultPassword = bCryptPasswordEncoder.encode("password" + UUID.randomUUID().toString().substring(0, 6));

            Userinfo userinfo = Userinfo.builder()
                                        .userinfoEmail(email)
                                        .userinfoPassword(defaultPassword)
                                     // .userprofile(Userprofile.builder().userprofileNickname(defaultNickname).build())  
                                        .build();

            List<UserRole> userRoles = new ArrayList<>();
            userRoles.add(UserRole.builder()
                                  .userRoleName("ROLE_USER")
                                  .userinfo(userinfo)
                                  .build());
            userinfo.setUserRoles(userRoles);
            userinfoRepository.save(userinfo);

            authenticationDto.setPassword(defaultPassword);
            authenticationDto.getRoleNames().add("ROLE_USER");
            return new OAuth2UserDetails(authenticationDto); 
        }
        
        authenticationDto.setPassword(findUserinfo.getUserinfoPassword());

        for(UserRole userRole : findUserinfo.getUserRoles()){
            authenticationDto.getRoleNames().add(userRole.getUserRoleName());
        }


        return new OAuth2UserDetails(authenticationDto);
    }
    
}
