package com.springsecurity.springsecuritypractice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.springsecuritypractice.entity.Userinfo;

public interface UserinfoRepository extends JpaRepository<Userinfo, Long>{


    Optional<Userinfo> findByUserinfoEmail(String email);
}
