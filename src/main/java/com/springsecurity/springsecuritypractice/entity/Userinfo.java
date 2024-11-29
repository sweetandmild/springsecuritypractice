package com.springsecurity.springsecuritypractice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@SequenceGenerator(name = "userinfo_SEQ", allocationSize = 1)
public class Userinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinfo_SEQ")
    private Long id;

    private String username;

    private String password; 

    private String role;
}
