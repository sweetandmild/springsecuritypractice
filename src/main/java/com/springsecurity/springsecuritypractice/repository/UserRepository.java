package com.springsecurity.springsecuritypractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.springsecuritypractice.entity.Userinfo;

public interface UserRepository extends JpaRepository<Userinfo, Long>{

}
