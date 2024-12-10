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
@AllArgsConstructor
@NoArgsConstructor

@Entity
@SequenceGenerator(name = "userinfo_role_seq", allocationSize = 1)
public class UserinfoRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinfo_role_seq")
    private Long userinfoRollId;

    private String rollName;
}
