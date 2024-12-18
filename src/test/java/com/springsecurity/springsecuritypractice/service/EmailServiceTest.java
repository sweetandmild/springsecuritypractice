package com.springsecurity.springsecuritypractice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService; 

    @Test
    void testGenerateVerificationCode() {

    }

    @Test
    void testSendVerificationCode() {
        emailService.sendVerificationCode("taesoo502@gmail.com");
    }

    @Test
    void testVerifyCode() {

    }
}
