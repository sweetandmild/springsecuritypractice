package com.springsecurity.springsecuritypractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.springsecuritypractice.dto.JoinDto;
import com.springsecurity.springsecuritypractice.entity.Userinfo;
import com.springsecurity.springsecuritypractice.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Transactional
    public void join(JoinDto dto){

        //db에 이미 동일한 username을 가진 회원이 존재하는지 체크

        System.out.println(dto.getUsername());

        Userinfo userinfo = Userinfo.builder()
        .username(dto.getUsername())
        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
        .role("ROLE_USER")
        .build();

        

        userRepository.save(userinfo);
    }
}
