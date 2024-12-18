package com.springsecurity.springsecuritypractice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.springsecuritypractice.entity.EmailVerification;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>{

    Optional<EmailVerification> findByEmailVerificationEmail(String email);

}
