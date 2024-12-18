package com.springsecurity.springsecuritypractice.service;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// @Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Gmail SMTP 서버 주소
        // mailSender.setHost("smtp.gmail.com");

        // // Gmail 사용자 계정 (아이디@지메일.com)
        // mailSender.setUsername("taesoo502@gmail.com");

        // // 앱 비밀번호(App Password) 또는 실제 패스워드 (보안을 위해 환경변수 등 외부에 저장 권장)
        // mailSender.setPassword("xotnwjd50215**");

        mailSender.setHost("smtp.naver.com");
        mailSender.setUsername("taesoo502@naver.com");  // 네이버 이메일 주소
        mailSender.setPassword("xotnwjd50215"); 

        // Gmail SMTP는 STARTTLS를 활용하는 587 포트 사용 권장
        mailSender.setPort(587);

        // 추가 프로퍼티 설정
        mailSender.setJavaMailProperties(getMailProperties());

        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        
        // 프로토콜 및 인증 설정
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");

        // STARTTLS 사용 (포트 587과 함께 사용)
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // 디버그 모드 (메일 전송 로그를 콘솔에서 확인할 수 있음)
        properties.setProperty("mail.debug", "true");

        // SSL 설정 (Gmail에서 포트 587을 이용할 때는 STARTTLS 사용을 권장하며, 아래 SSL 설정은 보통 꺼둡니다)
        // props.setProperty("mail.smtp.ssl.enable", "true"); // 포트465에서 'Implicit SSL' 방식 사용할 때 활성화
        // props.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");

        return properties;
    }
}
