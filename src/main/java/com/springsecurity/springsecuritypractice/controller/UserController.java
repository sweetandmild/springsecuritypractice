package com.springsecurity.springsecuritypractice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import com.springsecurity.springsecuritypractice.dto.JoinDto;
import com.springsecurity.springsecuritypractice.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginP(){

        return "login"; 
    }

    // @PostMapping("/loginProc")
    // public String loginProcess(){

    //     return "redirect:";
    // }

    @GetMapping("/join")
    public String joinP(){

        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDto dto){
        
        userService.join(dto);
        

        return "redirect:login";
    }

    @PostMapping("/api/user/login")
    public String login(){

        return "redirect:main";
    }
}
