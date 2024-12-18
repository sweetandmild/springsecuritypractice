package com.springsecurity.springsecuritypractice.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecuritypractice.entity.EmailVerification;
import com.springsecurity.springsecuritypractice.repository.EmailVerificationRepository;



@Service
public class EmailService {

    
    private EmailVerificationRepository emailVerificationRepository;

    private JavaMailSender mailSender;

    EmailService(){}

    @Autowired
    EmailService(EmailVerificationRepository emailVerificationRepository
                 , JavaMailSender mailSender){
        this.emailVerificationRepository = emailVerificationRepository;
        this.mailSender = mailSender;
    }

    public String generateVerificationCode(String email) {
        String verificationCode = String.format("%06d", new Random().nextInt(999999));

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmailVerificationEmail(email);
        emailVerification.setEmailVerificationCode(verificationCode);
        emailVerification.setEmailVerificationIsVerified(0);

        emailVerificationRepository.save(emailVerification);

        return verificationCode;
    }

    public boolean verifyCode(String email, String code) {
        Optional<EmailVerification> optionalVerification = emailVerificationRepository.findByEmailVerificationEmail(email);
        if (optionalVerification.isPresent()) {
            EmailVerification emailVerification = optionalVerification.get();
            if (emailVerification.getEmailVerificationCode().equals(code)) {
                emailVerification.setEmailVerificationIsVerified(1);
                emailVerificationRepository.save(emailVerification);
                return true;
            }
        }
        return false;
    }

    public String sendVerificationCode(String toEmail) {
        String verificationCode = generateVerificationCode(toEmail);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("taesoo502@naver.com");
        message.setTo(toEmail);
        message.setSubject("회원가입 인증 코드");
        message.setText("인증번호: " + verificationCode);

        mailSender.send(message);

        return verificationCode;
    }

}
