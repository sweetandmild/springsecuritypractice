package com.springsecurity.springsecuritypractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecuritypractice.repository.UserinfoRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{


    @Autowired
    private UserinfoRepository userinfoReposity; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return null;
    }

}
